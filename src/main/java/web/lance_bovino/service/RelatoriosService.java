package web.lance_bovino.service;

import javax.sql.DataSource;
import org.springframework.stereotype.Service;
import web.lance_bovino.report.JaspersoftUtil;

@Service
public class RelatoriosService {

    private DataSource dataSource;
    private JaspersoftUtil jaspersoftUtil;

    public RelatoriosService(DataSource dataSource, JaspersoftUtil jaspersoftUtil) {
        this.dataSource = dataSource;
        this.jaspersoftUtil = jaspersoftUtil;
    }

    public byte[] gerarRelatorioTodosGados() {
        return jaspersoftUtil.gerarRelatorio("/relatorios/relatoriogadoscompleto.jasper", null, dataSource);
    }
}