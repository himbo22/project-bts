package cdio.desert_eagle.project_bts.model;

public class StatusModel {
        private int avatarResId;
        private String username;
        private String statusText;
        private int statusImageResId;
        private int likeCount;
        private int commentCount;

        public StatusModel(int avatarResId, String username, String statusText, int statusImageResId, int likeCount, int commentCount) {
            this.avatarResId = avatarResId;
            this.username = username;
            this.statusText = statusText;
            this.statusImageResId = statusImageResId;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
        }

        public int getAvatarResId() {
            return avatarResId;
        }

        public String getUsername() {
            return username;
        }

        public String getStatusText() {
            return statusText;
        }

        public int getStatusImageResId() {
            return statusImageResId;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

}
