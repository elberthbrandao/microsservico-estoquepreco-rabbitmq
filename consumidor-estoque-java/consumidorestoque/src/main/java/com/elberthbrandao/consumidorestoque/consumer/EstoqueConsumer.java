package com.elberthbrandao.consumidorestoque.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constantes.RabbitMQConstantes;
import dto.EstoqueDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EstoqueConsumer {

    @RabbitListener(queues = RabbitMQConstantes.FILA_ESTOQUE)
    private void consumidor(String mensagem) throws JsonProcessingException {
        EstoqueDto estoqueDto = new ObjectMapper().readValue(mensagem, EstoqueDto.class);

        System.out.println(estoqueDto.codigoProduto);
        System.out.println(estoqueDto.quantidade);
        System.out.println("------------------------------------");
    }
}
