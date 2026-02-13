package com.banco.infrastructure.schedule.conta;

import com.banco.application.service.ContaService;
import com.banco.infrastructure.utils.LogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContaSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContaSchedule.class);
    private final ContaService contaService;


    public ContaSchedule(final ContaService contaService) {
        this.contaService = contaService;
    }

   @Scheduled(cron = "0 0 2 1 * * ")
    public void rendaFixa() {
        LOGGER.info(LogBuilder.of()
                .header("INICIANDO EXECUÇÃO DA RENDA FIXA")
                .build());
        contaService.rendaFixa();

        LOGGER.info(LogBuilder.of()
                .header("FINALIZANDO EXECUÇÃO DA RENDA FIXA")
                .build());


    }

}
