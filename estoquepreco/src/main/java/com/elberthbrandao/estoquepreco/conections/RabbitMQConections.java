package com.elberthbrandao.estoquepreco.conections;

import constantes.RabbitMQConstantes;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConections {
    private static final String NOME_EXCHANGE = "amq.direct";

    private AmqpAdmin admqpAdmin;

    public RabbitMQConections(AmqpAdmin amqpAdmin){
        this.admqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona() {
        Queue filaEstoque = this.fila(RabbitMQConstantes.FILA_ESTOQUE);
        Queue filaPreco= this.fila(RabbitMQConstantes.FILA_PRECO);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
        Binding ligacaoPreco = this.relacionamento(filaPreco, troca);

        //Criando as filas no RabbitMQ
        this.admqpAdmin.declareQueue(filaEstoque);
        this.admqpAdmin.declareQueue(filaPreco);

        this.admqpAdmin.declareExchange(troca);

        this.admqpAdmin.declareBinding(ligacaoEstoque);
        this.admqpAdmin.declareBinding(ligacaoPreco);
    }

}
