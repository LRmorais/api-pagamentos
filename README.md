# API de Pagamentos - README

Este é um projeto de uma API de Pagamentos desenvolvido em Java com o Spring Boot.

## Funcionalidades Implementadas

O sistema de pagamentos inclui as seguintes funcionalidades:

1. **Criação de Pagamentos:** Os usuários podem criar novos pagamentos especificando detalhes como código, tipo de documento, número de documento, número do cartão, método de pagamento e valor. O sistema valida as entradas e salva o pagamento se todos os critérios forem atendidos.

2. **Atualização de Status de Pagamento:** Os usuários podem atualizar o status de pagamento de acordo com regras específicas. Por exemplo, é possível atualizar um pagamento pendente para processado ou reverter um pagamento processado para pendente.

3. **Filtragem de Pagamentos:** Os usuários podem filtrar pagamentos com base em critérios como código, documento, status de pagamento, etc.

4. **Exclusão de Pagamentos:** Os usuários podem excluir pagamentos pendentes, desde que atendam a determinados critérios.

5. **Tratamento de Exceções:** O sistema lida com exceções como pagamento duplicado, pagamento já processado, pagamento não encontrado, erros internos, etc.

6. **Testes Unitários:** Foram implementados testes unitários para garantir a qualidade do código e verificar o tratamento adequado de exceções.

7. **Documentação:** A documentação da API foi implementada com o Swagger.

## Configuração

Antes de executar o projeto, siga estas etapas de configuração:

1. Clone o repositório: `git clone https://github.com/seu-usuario/sistema-pagamentos.git`
2. Sincronize as dependencias do maven.
4. Execute o projeto.
5. Acesse a Documentação em: http://localhost:8080/swagger-ui/index.html#/

## Uso

Após configurar e executar o projeto, você pode usar as APIs fornecidas para criar, atualizar, filtrar e excluir pagamentos.


