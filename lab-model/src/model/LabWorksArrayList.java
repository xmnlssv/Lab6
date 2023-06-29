package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class LabWorksArrayList {

    private ArrayList<LabWork> labWorks = new ArrayList<>();

    public LabWorksArrayList() {}

    public LabWorksArrayList(ArrayList<LabWork> labWorks) {
        this.labWorks = labWorks;
    }

    @XmlElement(name = "labWork")
    public ArrayList<LabWork> getLabWorks() {
        return labWorks;
    }

    public void setLabWorks(ArrayList<LabWork> labWorks) {
        this.labWorks = labWorks;
    }
}
