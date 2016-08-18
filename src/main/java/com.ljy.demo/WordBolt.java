package com.ljy.demo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Desc
 * @Author lijianying@youku.com
 * @Created-Time 8/16/16-5:32 PM.
 */
public class WordBolt extends BaseRichBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordBolt.class);
    OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
//            String word = (String)tuple.getString(0);
//            List<Object> list = tuple.getValues();
//            for (Object word1 : list) {
//
//                for (Object word : (List<Object>) word1) {
//                    LOGGER.info("bolt execute  : -------------" + word);
//                    if (Constant.wordCounter.containsKey(word)) {
//                        Constant.wordCounter.put((String) word, Constant.wordCounter.get((String) word) + 1);
//                    } else {
//                        Constant.wordCounter.put((String) word, 1);
//                    }
//                }
//            }
            String word = tuple.getString(0);
            if (Constant.wordCounter.containsKey(word)) {
                        Constant.wordCounter.put(word, Constant.wordCounter.get(word) + 1);
                    } else {
                        Constant.wordCounter.put(word, 1);
                    }
        } catch (Exception e) {
            LOGGER.error("wordBolt Error", e);
        }

        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public void cleanup() {
        LOGGER.info("Word Count Result--------:");

        List<Map.Entry<String,Integer>> entries = new ArrayList<>(Constant.wordCounter.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });

        for (Map.Entry<String,Integer> entry:entries) {
            if(entry.getValue()>2) {
                LOGGER.info(entry.getKey()+ "----" + entry.getValue());
            }
        }
        LOGGER.info("Word Count End -------");
    }
}
