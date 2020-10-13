package io.bissal.spring.elastic.test.service;

import io.bissal.spring.elastic.test.dao.CpuAndMemDao;
import io.bissal.spring.elastic.test.dao.ProcessListDao;
import io.bissal.spring.elastic.test.dao.ServerListDao;
import io.bissal.spring.elastic.test.model.elastic.server.CpuAndMem;
import io.bissal.spring.elastic.test.model.elastic.server.Server;
import io.bissal.spring.elastic.test.model.elastic.server.ServerDetail;
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
            CpuAndMem cpuAndMem = cpuAndMemDao.stat(hostId);

            Server server = new Server();
            server.setId(hostId);
            server.setCpu(cpuAndMem.getCpu());
            server.setMemory(cpuAndMem.getMemory());
            servers.add(server);
        }

        return servers;
    }

    public ServerDetail serverDetail(String serverId) {
        List<String> processes = processListDao.server(serverId);
        CpuAndMem cpuAndMem = cpuAndMemDao.stat(serverId);

        ServerDetail serverDetail = new ServerDetail();
        serverDetail.setId(serverId);
        serverDetail.setProcesses(processes);
        serverDetail.setCpu(cpuAndMem.getCpu());
        serverDetail.setMemory(cpuAndMem.getMemory());

        return serverDetail;
    }
}
