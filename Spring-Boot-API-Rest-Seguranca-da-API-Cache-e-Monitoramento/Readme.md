# Spring Boot API Rest: Segurança da API, Cache e Monitoramento

## Paginação e ordenação de recursos

### Paginação

Paginação serve para dividir o que vai ser devolvido na consulta (controla a quantidade do que vai ser devolvido).

**@RequestParam -** serve para avisar ao Spring que é um parâmetro de request (parâmetro de url), Automaticamente,
quando colocamos essa anotação, o Spring considera que o parâmetro é obrigatório, se não informado dispara o erro 404.

✅ Para realizar paginação com Spring Data JPA, devemos utilizar a interface Pageable

### Ordenação

O Spring Spring também embutiu essa questão de ordenação na classe PageRequest em um dos métodos of()

**@EnableSpringDataWebSupport -** Com essa anotação, habilitamos o suporte para o Spring pegar da requisição dos
parâmetros da url, os campos, as informações de paginação e ordenação, e repassar isso para o Spring data.

**@PageableDefault -** Serve para indicar o padrão de paginação/ordenação ao Spring, quando o cliente da API não enviar tais
informações