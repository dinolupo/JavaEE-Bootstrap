package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/**
 * Created by dinolupo.github.io on 23/06/16.
 */
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class BigBrotherJPA {

    CopyOnWriteArrayList<String> messageQueue;

    @Inject
    MessageAnalyzer messageAnalyzer;

    @Inject
    MessageArchive messageArchiver;

    @Resource
    TimerService timerService;
    Timer timer;

    @Inject
    Event<String> event;

    @Resource
    SessionContext sessionContext;

    @PostConstruct
    public void initialize(){
        this.messageQueue = new CopyOnWriteArrayList<>();
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.second("*/5").minute("*").hour("*");
        for (Timer timer1 : timerService.getAllTimers()) {
            System.out.println(">>>>>> Timer: " + timer1);
        }
        this.timer = timerService.createCalendarTimer(scheduleExpression);
    }


    public void gatherEverything(String message){
        messageArchiver.saveMessage(message);
        messageQueue.add(message);
        event.fire(message);

        //sessionContext.setRollbackOnly();

    }

    @Timeout
    public void batchAnalyze(){
        System.out.printf("**************** Analyzing at %s ***************\n", new Date());
        // collector of results from the analyzer
        List<Future<Boolean>> results = new ArrayList<>();
        for (String message: messageQueue) {

            // Asynchronous call to analyze the message
            results.add(messageAnalyzer.analyze(message));

            messageQueue.remove(message);
        }

        // show the results asynchronously
        messageAnalyzer.showResults(results);
    }

    // --------------------------------------------------------
    //  the following method is very important because without
    //  it the Timer will never be destroyed and on following
    //  redeployments you will obtain multiple timers
    // --------------------------------------------------------

    @PreDestroy
    void preDestroy() {
        System.err.println("*************************** DESTROYING TIMER ***********************************");
        timer.cancel();
        timer = null;
    }

}
