package com.example.apiRelatorios.service;


import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Service
public class RelatorioInspecaoService {

    @Value("classpath:reports/Blank_A4.jrxml")
    private Resource relatorioInspecao;

    @Autowired
    private DataSource dataSouce;

    public byte [] gerarRelatorio(Long idInspecao) {

        try(
                Connection connection = dataSouce.getConnection();
        ) {

            JasperReport compiledReport =  JasperCompileManager.compileReport(relatorioInspecao.getInputStream());


            Map<String,Object> parametros = new HashMap<>();

            parametros.put("vistoria",idInspecao);

            JasperPrint print = JasperFillManager.fillReport(compiledReport,parametros,connection);

            return JasperExportManager.exportReportToPdf(print);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;

    }



}