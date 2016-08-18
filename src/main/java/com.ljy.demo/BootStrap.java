package com.ljy.demo;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootStrap {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(BootStrap.class);

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("wordSpout", new WordSpout(),1);
        builder.setBolt("wordFormat", new WordFormat(), 2).shuffleGrouping("wordSpout",Constant.WORD_SPOUT);
        builder.setBolt(Constant.WORD_BOLT, new WordBolt(), 4).shuffleGrouping("wordFormat",Constant.WORD_FORMAT);

        Config config = new Config();
        config.setDebug(false);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("FirstTopo", config, builder.createTopology());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WordSpout.open = false;
    }
}
