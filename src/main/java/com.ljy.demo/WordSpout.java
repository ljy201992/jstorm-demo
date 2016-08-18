package com.ljy.demo;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Desc
 * @Author lijianying@youku.com
 * @Created-Time 8/16/16-5:23 PM.
 */
public class WordSpout extends BaseRichSpout {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordSpout.class);
    public static volatile boolean open = true;

    SpoutOutputCollector collector;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
            String values = WordGenerator.generateString();
            LOGGER.info("spout values : " + values);
            this.collector.emit(Constant.WORD_SPOUT, new Values(values));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream(Constant.WORD_SPOUT, new Fields(Constant.WORD_SPOUT_FIELDS));
    }
}
