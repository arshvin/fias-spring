package medved.fias.scheduling.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by arshvin on 25.06.16.
 */
public class DownloadFiasArchiveJob implements Job {

    private final String downloadUrl = "http://fias.nalog.ru/Public/Downloads/Actual/fias_xml.rar";
    private final String checkUrl = "http://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
