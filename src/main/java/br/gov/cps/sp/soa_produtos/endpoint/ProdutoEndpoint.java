package br.gov.cps.sp.soa_produtos.endpoint;

import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoRequest;
import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoResponse;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProdutoEndpoint {

    private static final String NAMESPACE = "http://cps.sp.gov.br/produtos";

    @PayloadRoot(namespace = NAMESPACE, localPart = "consultarProdutoRequest")
    @ResponsePayload
    public ConsultarProdutoResponse consultarProduto(@RequestPayload ConsultarProdutoRequest request) {

        ConsultarProdutoResponse response = new ConsultarProdutoResponse();

        switch (request.getCodigo()) {
            case 1:
                response.setNome("Notebook Inspiron 15");
                response.setDescricao("Notebook com 16GB de RAM e SSD de 512GB");
                response.setMarca("Dell");
                response.setEstoque(25);
                break;
            case 2:
                response.setNome("Mouse sem fio M170");
                response.setDescricao("Mouse óptico sem fio com receptor USB");
                response.setMarca("Logitech");
                response.setEstoque(120);
                break;
            case 3:
                response.setNome("Smartphone A15");
                response.setDescricao("Smartphone samsung a 15 com 128 GB de armzenamento");
                response.setMarca("Samsung");
                response.setEstoque(130);
                break;
            case 4 :
                response.setNome("Teclado mecanico");
                response.setDescricao("teclado mecanico logtech");
                response.setMarca("logtech");
                response.setEstoque(130);
                break;
            case 5:
                response.setNome("Teclado mecanico");
                response.setDescricao("teclado mecanico logtech");
                response.setMarca("logtech");
                response.setEstoque(130);
                break;
            default:
                response.setNome("Produto não encontrado");
                response.setDescricao("-");
                response.setMarca("-");
                response.setEstoque(0);
        }

        return response;
    }
}