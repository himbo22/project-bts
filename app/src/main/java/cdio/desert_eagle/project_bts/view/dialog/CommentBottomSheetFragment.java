package cdio.desert_eagle.project_bts.view.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.adapter.CommentAdapter;
import cdio.desert_eagle.project_bts.model.request.CommentRequest;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;
import cdio.desert_eagle.project_bts.viewmodel.CommentViewModel;

public class CommentBottomSheetFragment extends BottomSheetDialogFragment {
    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior<View> bottomSheetBehavior;
    View rootView;
    ImageView imgClose;
    RecyclerView rvComment;
    EditText etComment;
    CommentViewModel commentViewModel;
    CommentAdapter commentAdapter;
    ConstraintLayout clComment;
    AppCompatButton btnComment;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar pbLoading;
    List<CommentResponse> commentResponseList = new ArrayList<>();
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private final Long postId;
    private final Long userId;

    public CommentBottomSheetFragment(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return bottomSheetDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.comment_bottom_sheet, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // inflate view
        rvComment = view.findViewById(R.id.rvComment);
        imgClose = view.findViewById(R.id.imgClose);
        etComment = view.findViewById(R.id.etComment);
        clComment = view.findViewById(R.id.clComment);
        btnComment = view.findViewById(R.id.btnComment);
        pbLoading = view.findViewById(R.id.pbLoading);

        // show progress bar
        pbLoading.setVisibility(View.VISIBLE);


        // init data
        commentViewModel = new CommentViewModel();
        commentAdapter = new CommentAdapter(requireContext(), commentResponseList);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());

        // set up adapter
        rvComment.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComment.setAdapter(commentAdapter);

        // call api
        commentViewModel.getPostComments(this.postId, 0, 20);


        // set behavior to expand on the screen
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetBehavior.setDraggable(false);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        // set min height to parent view
        CoordinatorLayout layout = bottomSheetDialog.findViewById(R.id.bottomSheetLayout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        // observer data
        commentViewModel.commentResponseLiveData.observe(requireActivity(), data -> {
            commentAdapter.updateData(data);
            loading = true;
            pbLoading.setVisibility(View.GONE);
        });


        commentViewModel.commentAdditionalLiveData.observe(requireActivity(), data -> {
            if (data != null) {
                commentAdapter.addSingleData(new CommentResponse(
                        data.getId(),
                        data.getAuthor().getAvatar(),
                        data.getAuthor().getUsername(),
                        data.getContent()
                ));
                rvComment.smoothScrollToPosition(Objects.requireNonNull(rvComment.getAdapter()).getItemCount());
                pbLoading.setVisibility(View.GONE);
            }
        });

        // on scroll recyclerview
        rvComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    layoutManager = rvComment.getLayoutManager();
                    assert layoutManager != null;
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if (loading) {
                        pbLoading.setVisibility(View.VISIBLE);
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            commentViewModel.loadMoreComments(postId, 20);
                        }
                    }
                }
            }
        });

        // handle onclick
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etComment.getText().toString().isBlank()) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss+HH:mm", new Locale("vie", "VNM"));
                        Calendar calendar = Calendar.getInstance();
                        Date now = calendar.getTime();
                        String timeStamp = simpleDateFormat.format(now);
                        commentViewModel.addComment(new CommentRequest(
                                etComment.getText().toString(),
                                timeStamp,
                                userId,
                                postId)
                        );
                        etComment.getText().clear();
                    }
                }
            }
        });
    }
}
