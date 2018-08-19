package com.ghost.thunder;

public class Download {
    long taskId, mFileSize, mDownloadSize, mDownloadSpeed, mAdditionalResDCDNSpeed;
    String path, url, filename;

    public Download(long taskId, String url, String path, String filename) {
        super();
        this.taskId = taskId;
        this.url = url;
        this.path = path;
        this.filename = filename;
    }

    public Download(){
        super();
    }

    /*
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }
    */
}
