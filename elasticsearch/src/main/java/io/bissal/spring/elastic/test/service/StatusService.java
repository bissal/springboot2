package io.bissal.spring.elastic.test.service;

import io.bissal.spring.elastic.test.dao.CpuAndMemDao;
import io.bissal.spring.elastic.test.dao.ProcessListDao;
import io.bissal.spring.elastic.test.dao.ServerListDao;
import io.bissal.spring.elastic.test.model.elastic.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {
    @Autowired
    private ServerListDao serverListDao;
    @Autowired
    private CpuAndMemDao cpuAndMemDao;

    @Autowired
    private ProcessListDao processListDao;

    public List<Server> list() {
        List<String> serverIds = serverListDao.searchEachServers();
        List<Server> servers = new ArrayList<>();
        for (String hostId : serverIds) {
            Server server = cpuAndMemDao.stat(hostId);
            servers.add(server);
        }

        return servers;
    }

    public List<String> status(String hostId) {
        List<String> processes = processListDao.server(hostId);

        return processes;
    }
}
