package com.example.lin_new_project.room.data;

public class HistoryData {
    public String historyName = "";//儲存ProgramName
    private int ProgramId;//Program Code,原先名稱baseProgramId

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public int getProgramId() {
        return ProgramId;
    }

    public void setProgramId(int programId) {
        ProgramId = programId;
    }


}
