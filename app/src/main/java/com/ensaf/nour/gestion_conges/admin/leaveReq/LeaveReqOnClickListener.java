package com.ensaf.nour.gestion_conges.admin.leaveReq;

public interface LeaveReqOnClickListener {

    void onAccept(int pos);
    void onReject(int pos);
    void onCancel(int pos);

}
