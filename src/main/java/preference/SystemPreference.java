package preference;

import javafx.scene.control.TableView;
import models.DownloadTask;
import models.XmlDownloadTask;
import windows.MainWindow;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SystemPreference {

    String fileName = "state.xml";

    public void saveApplicationState() {
        TableView<DownloadTask> tableView = MainWindow.getInstance().getTableView();
        JAXBContext contextObj = null;
        try {
            contextObj = JAXBContext.newInstance(XmlDownloadTask.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            XmlDownloadTask xmlDownloadTasks= new XmlDownloadTask();

            for (DownloadTask task : tableView.getItems()) {
               xmlDownloadTasks.setDownloadTasks(task);
            }
            File file = new File(fileName);
            marshallerObj.marshal(xmlDownloadTasks,file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public XmlDownloadTask getApplicationState() {

        File xmlFile = new File(fileName);
        XmlDownloadTask xmlDownloadTasks = null;
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlDownloadTask.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            if(xmlFile == null)
                return null;
            xmlDownloadTasks = (XmlDownloadTask) jaxbUnmarshaller.unmarshal(xmlFile);
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
        return xmlDownloadTasks;
    }

}