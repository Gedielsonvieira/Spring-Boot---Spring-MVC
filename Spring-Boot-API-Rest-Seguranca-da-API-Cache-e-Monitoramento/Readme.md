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

**@PageableDefault -** Serve para indicar o padrão de paginação/ordenação ao Spring, quando o cliente da API não enviar
tais
informações

## Melhorando desempenho com Spring Cache

> A ideia é que, ao utilizar o cache, conseguimos dizer para o Spring guardar o retorno de um método em cache. Na
> primeira vez que acionarmos aquele método, ele vai executar linha por linha do método e vai devolver o resultado.
> Porém,
> nas próximas chamadas, ele já devolve direto o que está em memória.

Nesta aplicação estamos utilizando um provedor padrão do Spring que não é recomendado para usar em produção, então com
uma aplicação rodando em produção, o ideal é utilizar algum provedor de cache.

Além de declararmos a dependencia de cache, precisamos habilitar o uso de cache na aplicação com a anotação
**@EnableCaching** na classe main.

**@Cacheable -** Serve para avisar o Spring para guardar o retorno do método em cache e nesta anotação precisamos passar
uma String que vai ser o identificador unico deste cache, isto para diferenciar de outros métodos que terão a anotação
@Cacheable.

**Para sabermos que realmente o retorno do método está sendo guardado em cache e o que está guardado no cache está sendo
devolvido, como o método esta fazendo um consulta ao BD podemos habilitar o log do Hibernate para ele sempre imprimir
toda vez que ele for fazer uma chamada no banco de dados.**

### Inserir no arquivo application.properties:

**spring.jpa.properties.hibernate.show_sql=true -** Isso fala para o hibernate imprimir os comandos SQL que ele dispara
toda vez que acessa o banco de dados
**spring.jpa.properties.hibernate.format_sql=true -** Deixa o SQL formatado.