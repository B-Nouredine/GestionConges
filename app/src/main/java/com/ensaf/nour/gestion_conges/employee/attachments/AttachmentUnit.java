package com.ensaf.nour.gestion_conges.employee.attachments;

import android.net.Uri;

public class AttachmentUnit {

    private String attachmentName;
    private long attachmentDate;
    private Uri attachmentUri;
    private boolean showPreview;

    public AttachmentUnit(String attachmentName, long attachmentDate, Uri attachmentUri, boolean showPreview) {
        this.attachmentName = attachmentName;
        this.attachmentDate = attachmentDate;
        this.attachmentUri = attachmentUri;
        this.showPreview = showPreview;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public long getAttachmentDate() {
        return attachmentDate;
    }

    public void setAttachmentDate(long attachmentDate) {
        this.attachmentDate = attachmentDate;
    }

    public Uri getAttachmentUri() {
        return attachmentUri;
    }

    public void setAttachmentUri(Uri attachmentUri) {
        this.attachmentUri = attachmentUri;
    }

    public boolean isShowPreview() {
        return showPreview;
    }

    public void setShowPreview(boolean showPreview) {
        this.showPreview = showPreview;
    }
}
