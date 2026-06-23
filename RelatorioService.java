package web.lance_bovino.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

// Importe as suas classes de modelo e repositório (ajuste o pacote se necessário)
import web.lance_bovino.model.Gado;
import web.lance_bovino.repository.GadoRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    @Autowired
    private GadoRepository gadoRepository;

    public byte[] gerarRelatorioTodosGados() {
        try {
            // 1. Busca o arquivo compilado do Jasper dentro do projeto
            ClassPathResource cpr = new ClassPathResource("relatorios/RelatorioGados.jasper");
            InputStream arquivoJasper = cpr.getInputStream();

            // 2. Busca todos os gados do banco de dados (A Coleção)
            List<Gado> gados = gadoRepository.findAll();

            // 3. Converte a lista para o formato que o Jasper entende
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(gados);

            // 4. Parâmetros extras
            Map<String, Object> parametros = new HashMap<>();

            // 5. Preenche o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, parametros, dataSource);

            // 6. Exporta para PDF em array de bytes
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}