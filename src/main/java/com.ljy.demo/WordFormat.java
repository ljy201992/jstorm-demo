package com.ljy.demo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Desc
 * @Author lijianying@youku.com
 * @Created-Time 8/16/16-5:32 PM.
 */
public class WordFormat extends BaseRichBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordFormat.class);
    OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String allWords = tuple.getStringByField(Constant.WORD_SPOUT_FIELDS);
            LOGGER.info("WordFormat : tuple.getStringByField() : " + allWords);
            if(!Strings.isNullOrEmpty(allWords)){
                String[] words = allWords.split(",");
                for(String word : words){
                    this.collector.emit(Constant.WORD_FORMAT,new Values(word));
                }
            }
        } catch (Exception e) {
            LOGGER.error("WordFormat Error", e);
        }
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream(Constant.WORD_FORMAT, new Fields(Constant.WORD_BOLT_FIELDS));
    }

    @Override
    public void cleanup() {
    }
}
