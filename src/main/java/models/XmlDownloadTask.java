package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class XmlDownloadTask{

    @XmlElement(name = "downloadTask")
    List<DownloadTask> downloadTasks = new ArrayList<>();

    public List<DownloadTask> getDownloadTasks(){
        return downloadTasks;
    }

    public void setDownloadTasks(DownloadTask task){
        downloadTasks.add(task);
    }

}
