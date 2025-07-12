package classifica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classifica implements Serializable {

    private List<Record> records;

   
    public Classifica() {
        records = new ArrayList<>();
    }


    public void addRecord(Record record) {
        records.add(record);
    }

 
    public List<Record> getRecords() {
        return records;
    }

  
    public void setRecords(List<Record> records) {
        this.records = records;
    }

    
    public String[][] getRecordsAsMatrix() {
        String[][] array = new String[records.size()][3];
        for (int i = 0; i < records.size(); i++) {
            array[i][0] = records.get(i).getNome();
            array[i][1] = records.get(i).timeFormatted();
            array[i][2] = records.get(i).getData();
        }
        return array;
    }
}

