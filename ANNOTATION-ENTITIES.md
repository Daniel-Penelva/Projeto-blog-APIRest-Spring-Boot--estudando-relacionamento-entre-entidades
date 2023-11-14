# Anotação `@MappedSuperclass`

A anotação `@MappedSuperclass` é usada em classes que não são entidades por si só, mas que fornecem atributos comuns para as classes que a estendem. Essa anotação é frequentemente usada em conjunto com o framework de mapeamento objeto-relacional (ORM) JPA (Java Persistence API).

A anotação `@MappedSuperclass` é colocada na classe pai que contém atributos comuns a várias classes filhas. Quando uma classe estende uma classe anotada com `@MappedSuperclass`, ela herda os atributos da classe pai, e esses atributos são mapeados para o banco de dados como se fossem parte da classe filha.

Exemplo simples:

```java
import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // outros atributos comuns, métodos getters e setters, etc.
}
```

A classe acima, `BaseEntity`, é anotada com `@MappedSuperclass` e contém um campo `id` comum para várias entidades. Agora, imagine que você tenha duas entidades, por exemplo, `User` e `Product`, que precisam de um identificador. Em vez de repetir o código para o campo `id` em ambas as classes, você pode simplesmente estender `BaseEntity`:

```java
import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {

    private String username;

    // outros atributos específicos de User, métodos getters e setters, etc.
}
```

```java
import javax.persistence.Entity;

@Entity
public class Product extends BaseEntity {

    private String productName;

    // outros atributos específicos de Product, métodos getters e setters, etc.
}
```

Dessa forma, `User` e `Product` herdam o campo `id` e outras características definidas em `BaseEntity`, evitando duplicação de código e facilitando a manutenção. Essa abordagem é particularmente útil em situações em que você tem atributos comuns entre várias entidades em seu modelo de dados.

# Anotação `@EntityListeners`

A anotação `@EntityListeners` em Java é usada em conjunto com a JPA (Java Persistence API) para especificar uma ou mais classes de ouvintes que serão notificadas quando eventos relacionados a uma entidade ocorrerem. Esses eventos podem incluir a criação, atualização ou remoção de entidades. O ouvinte é uma classe que implementa determinadas interfaces ou métodos específicos para lidar com esses eventos.

Quando você utiliza `@EntityListeners` com uma classe de ouvinte específica, você está dizendo à JPA para notificar essa classe quando certos eventos acontecerem em relação à entidade à qual a anotação está associada.

A propriedade comum usada com `@EntityListeners` é a classe de ouvinte em si. Por exemplo:

```java
@EntityListeners(AuditingEntityListener.class)
public class YourEntity {
    // ... código da entidade ...
}
```

Neste exemplo, `AuditingEntityListener.class` é a classe de ouvinte que será notificada quando eventos relacionados à entidade `YourEntity` ocorrerem. Esse tipo de configuração é frequentemente usado em conjunto com recursos de auditoria, como rastreamento de quem criou ou modificou uma entidade e quando isso aconteceu.

Além disso, é importante observar que o `@EntityListeners` pode ser aplicado em nível de classe ou em nível de campo. Se aplicado em nível de campo, ele especifica ouvintes para eventos específicos relacionados a esse campo.

Além da configuração básica, outras propriedades podem ser usadas para personalizar o comportamento dos ouvintes, dependendo da implementação específica. Por exemplo, ao usar a funcionalidade de auditoria, você pode configurar detalhes como a data de criação, a data de modificação, etc. Essas configurações podem variar dependendo do provedor JPA que você está utilizando (por exemplo, Hibernate, EclipseLink).

No contexto de auditoria, a anotação `@EntityListeners(AuditingEntityListener.class)` geralmente está associada à capacidade de auditar entidades automaticamente, registrando informações como data de criação, data de modificação, quem criou a entidade, etc. Essa é uma maneira de introduzir um controle de auditoria nas entidades persistidas.

# Anotação `@EntityListeners`

A anotação `@JsonIgnoreProperties` em Java é usada para ignorar propriedades específicas durante a serialização de um objeto para JSON ou durante a desserialização de JSON para um objeto. Ela é frequentemente utilizada em conjunto com bibliotecas de mapeamento JSON, como o Jackson.

A anotação `@JsonIgnoreProperties` permite que você especifique uma lista de nomes de propriedades que devem ser ignorados durante a serialização ou desserialização. No exemplo abaixo:

```java
@JsonIgnoreProperties(value = {"dataCriacao", "dataAtualizacao"}, allowGetters = true)
public class SuaClasse {
    // ... código da classe ...
}
```

Neste exemplo, a classe `SuaClasse` está anotada com `@JsonIgnoreProperties`. Isso significa que, ao converter essa classe para JSON, as propriedades "dataCriacao" e "dataAtualizacao" serão excluídas do resultado JSON.

Explicando os parâmetros usados no exemplo:

- `value`: Especifica um array de nomes de propriedades que devem ser ignorados durante a serialização e desserialização. No exemplo, "dataCriacao" e "dataAtualizacao" são as propriedades que serão ignoradas.

- `allowGetters`: Define se os métodos getter correspondentes a essas propriedades devem ser considerados. Se `allowGetters` for definido como `true`, os getters correspondentes ainda serão usados para obter os valores das propriedades mesmo que as próprias propriedades sejam ignoradas. Neste exemplo, `allowGetters` está definido como `true`, indicando que os getters associados a "dataCriacao" e "dataAtualizacao" serão chamados, mesmo que essas propriedades sejam ignoradas.

Este é um recurso útil quando você deseja controlar se determinadas propriedades devem ou não ser incluídas no JSON resultante ou se devem ser ignoradas durante a desserialização. Isso é comumente usado em situações em que você tem propriedades que não devem ser expostas publicamente ou que não são relevantes para a camada de apresentação da sua aplicação.

# Anotação `@Temporal`

A anotação `@Temporal` em Java é usada em conjunto com campos de data e hora em entidades JPA (Java Persistence API) para mapear como esses campos são persistidos no banco de dados. Ela define o tipo temporal da propriedade, ou seja, se representa uma data, uma hora ou ambos.

A anotação `@Temporal` aceita o parâmetro `value`, que deve ser um membro da enumeração `TemporalType`. Os principais valores dessa enumeração são `DATE`, `TIME` e `TIMESTAMP`.

- `TemporalType.DATE`: Indica que o campo representa apenas a data, sem incluir informações sobre a hora.
  
  Exemplo:
  ```java
  @Temporal(TemporalType.DATE)
  private Date data;
  ```

- `TemporalType.TIME`: Indica que o campo representa apenas a hora, sem incluir informações sobre a data.

  Exemplo:
  ```java
  @Temporal(TemporalType.TIME)
  private Date hora;
  ```

- `TemporalType.TIMESTAMP`: Indica que o campo representa tanto a data quanto a hora.

  Exemplo:
  ```java
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataEHora;
  ```

Estes são os principais valores que pode usar com `@Temporal`. O objetivo é fornecer informações ao provedor JPA sobre como as datas e horas devem ser persistidas no banco de dados. Essa anotação é frequentemente utilizada em conjunto com campos do tipo `java.util.Date` ou `java.util.Calendar`.

Se estiver usando Java 8 ou posterior, com as APIs de data e hora modernas (`java.time`), pode-se usar a anotação `@Column` com o atributo `columnDefinition` para especificar como o tipo de banco de dados deve armazenar o valor. Por exemplo:

```java
@Column(columnDefinition = "TIMESTAMP")
private LocalDateTime dataEHora;
```

Isso elimina a necessidade de `@Temporal` ao trabalhar com `LocalDateTime` do Java 8 em diante, pois o mapeamento para o banco de dados é mais direto.

# Anotação `@CreatedDate`

A anotação `@CreatedDate` faz parte do projeto Spring Data, que é uma extensão do Spring Framework para simplificar o acesso a dados em ambientes Java baseados em JPA, MongoDB, Neo4j e outros.

A anotação `@CreatedDate` é comumente usada em conjunto com a funcionalidade de auditoria provida pelo Spring Data. Essa anotação é usada para marcar um campo em uma classe de entidade como a representação da data e hora em que a entidade foi criada.

Quando aplica `@CreatedDate` a um campo em uma classe de entidade, o Spring Data automaticamente popula esse campo com a data e hora em que a entidade é persistida pela primeira vez. Isso é útil para rastrear quando um registro foi inicialmente criado no banco de dados.

Exemplo básico:

```java
@Entity
public class SeuObjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    // outros atributos, construtores, getters e setters
}
```

Neste exemplo, a classe `SeuObjeto` possui um campo `dataCriacao` marcado com `@CreatedDate`. Quando um objeto dessa classe é persistido pela primeira vez, o Spring Data automaticamente preenche esse campo com a data e hora atuais.

Para habilitar a funcionalidade de auditoria do Spring Data, normalmente precisa configurar um `AuditingEntityListener` e habilitar a auditoria em sua aplicação. Isso é feito adicionando a anotação `@EnableJpaAuditing` em uma configuração ou classe principal da aplicação.

```java
@SpringBootApplication
@EnableJpaAuditing
public class SuaAplicacao {
    public static void main(String[] args) {
        SpringApplication.run(SuaAplicacao.class, args);
    }
}
```

Com isso configurado, o Spring Data cuidará automaticamente de preencher campos anotados com `@CreatedDate` e também outros campos relacionados à auditoria, como `@LastModifiedDate` para rastrear a última modificação da entidade. Isso oferece uma maneira conveniente de manter informações de auditoria em suas entidades sem a necessidade de lidar manualmente com esses detalhes em cada operação de persistência.

# Anotação `@LastModifiedDate`

A anotação `@LastModifiedDate` faz parte do projeto Spring Data, que é uma extensão do Spring Framework para simplificar o acesso a dados em ambientes Java baseados em JPA, MongoDB, Neo4j e outros.

Assim como `@CreatedDate`, a anotação `@LastModifiedDate` é geralmente usada em conjunto com a funcionalidade de auditoria fornecida pelo Spring Data. Essa anotação é usada para marcar um campo em uma classe de entidade como a representação da data e hora da última modificação da entidade.

Quando aplica `@LastModifiedDate` a um campo em uma classe de entidade, o Spring Data automaticamente atualiza esse campo com a data e hora sempre que a entidade é modificada e persistida novamente. Isso é útil para rastrear quando uma entidade foi pela última vez alterada no banco de dados.

Exemplo básico:

```java
@Entity
public class SeuObjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // outros atributos

    @LastModifiedDate
    @Column(name = "data_ultima_modificacao")
    private LocalDateTime dataUltimaModificacao;

    // outros atributos, construtores, getters e setters
}
```

Neste exemplo, a classe `SeuObjeto` possui um campo `dataUltimaModificacao` marcado com `@LastModifiedDate`. Quando um objeto dessa classe é modificado e persistido, o Spring Data automaticamente atualiza esse campo com a data e hora atuais.

Da mesma forma que com `@CreatedDate`, para habilitar a funcionalidade de auditoria do Spring Data, geralmente precisa configurar um `AuditingEntityListener` e habilitar a auditoria em sua aplicação, adicionando a anotação `@EnableJpaAuditing` em uma configuração ou classe principal da aplicação.

```java
@SpringBootApplication
@EnableJpaAuditing
public class SuaAplicacao {
    public static void main(String[] args) {
        SpringApplication.run(SuaAplicacao.class, args);
    }
}
```

Com isso configurado, o Spring Data cuidará automaticamente de preencher campos anotados com `@LastModifiedDate` e outros campos relacionados à auditoria, proporcionando uma maneira conveniente de rastrear a última modificação de suas entidades sem a necessidade de lidar manualmente com esses detalhes em cada operação de persistência.

# Super Class AuditModel
É uma classe base (ou classe pai) que é projetada para ser estendida por outras classes de entidade em um ambiente que utiliza a JPA (Java Persistence API), especialmente em conjunto com recursos de auditoria e mapeamento objeto-relacional.

```java
package com.apirest.blog.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "dataCriacao", "dataAtualizacao" }, allowGetters = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreatedDate
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false)
    @LastModifiedDate
    private Date dataAtualizacao;
}
```

Principais elementos dessa classe:

1. **`@MappedSuperclass`**: Essa anotação indica que a classe `AuditModel` é uma classe base para entidades e que seus campos podem ser mapeados para colunas de tabelas de entidades que estendem `AuditModel`. Isso significa que os campos desta classe podem ser herdados por outras entidades sem a necessidade de repetir a definição desses campos em cada entidade.

2. **`@EntityListeners(AuditingEntityListener.class)`**: Indica que a classe usa um ouvinte de entidade para eventos de auditoria. O `AuditingEntityListener` é frequentemente associado às anotações `@CreatedDate` e `@LastModifiedDate`, que são usadas para rastrear a data de criação e a última data de modificação das entidades.

3. **`@JsonIgnoreProperties(value = { "dataCriacao", "dataAtualizacao" }, allowGetters = true)`**: Essa anotação indica que, ao serializar a instância desta classe para JSON (por exemplo, ao responder a uma solicitação HTTP), os campos `dataCriacao` e `dataAtualizacao` devem ser ignorados. `allowGetters = true` permite que os getters desses campos sejam usados na serialização, mesmo que os campos em si sejam ignorados.

4. **`@Getter` e `@Setter`**: Essas anotações são do projeto Lombok, geram automaticamente métodos getters e setters para os campos da classe. Isso reduz a quantidade de código boilerplate.

5. **`@AllArgsConstructor` e `@NoArgsConstructor`**: Essas anotações, também do Lombok, geram automaticamente construtores com todos os argumentos e construtores sem argumentos, respectivamente.

6. **Campos de Data com Anotações de Auditoria**:
   - `dataCriacao`: Campo que armazena a data de criação da entidade. A anotação `@Temporal(TemporalType.TIMESTAMP)` indica que o campo representa data e hora.
   - `dataAtualizacao`: Campo que armazena a data da última modificação da entidade. Também usa `@Temporal(TemporalType.TIMESTAMP)` para representar data e hora.

Em resumo, essa classe `AuditModel` fornece funcionalidades comuns de auditoria para entidades JPA, incluindo rastreamento de data de criação e data da última modificação. Outras entidades podem estender `AuditModel` para herdar automaticamente essas características sem a necessidade de repetir a definição de campos e comportamentos de auditoria em cada entidade.