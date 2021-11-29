package test.adnuntius.trafficcounter.summary;

import java.util.List;

public class SummaryLogWriter {

    private List<SummaryLogItem> logItems;

    public SummaryLogWriter(List<SummaryLogItem> logItems) {
        this.logItems = logItems;
    }

    public void print() {
        logItems.stream().forEach(item -> System.out.println(item.getLog()));
        System.out.println("=================================================");

    }
}
