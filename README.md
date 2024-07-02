
![Logo](https://th.bing.com/th/id/OIP.z6PmSPM7Qh6EORNSzBh51AHaCu?rs=1&pid=ImgDetMain)


# Back-end: Rest API de eventos de tecnologia no Brasil 🚀
API onde é possível realizar filtragem de eventos que ainda irão acontecer, cadastrar novos eventos com possibilidade de registrar o endereço caso esses não sejam remotos e acoplar cupons a eventos para obter desconto.


## Documentação da API 🔍

### 1. Retornar todos os eventos

```http
  GET /api/event?page={value1}&size={value2}
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `page` | `string` | *Opcional*: Quantidade de páginas. Valor padrão 0 |
| `size` | `string` | *Opcional*: Quantidade de items em cada página. Valor padrão 10 |


### 2. Filtrar eventos

```http
  GET /api/event/filter?page={value1}&size={value2}
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `page` | `string` | *Opcional*: Quantidade de páginas. Valor padrão 0 |
| `size` | `string` | *Opcional*: Quantidade de items em cada página. Valor padrão 10 |

### 3. Criar evento

```http
  POST /api/event
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `title` | `string` | Título do evento(Obrigatório) |
| `description` | `string` | Descrição do evento(Opcional) |
| `date` | `long` | Data marcada do evento(Obrigatório) |
| `city` | `string` | Cidade(Obrigatório) |
| `uf` | `string` | Estado(Obrigatório) |
| `remote` | `boolean` | Remoto ou presencial(Obrigatório) |
| `eventUrl` | `string` | URL para visualizar ou se cadastrar no evento(Obrigatório) |
| `image` | `multipartFile` | Banner do evento(Opcional) |

Corpo da requição:
```json
    {
        "title": String,
        "description": String,
        "date": String,
        "city": String,
        "uf": String,
        "remote": Boolean,
        "eventUrl": String,
        "image": File
    }
```
## Tecnologias utilizadas 💻
- Java
- Spring Boot
- AWS S3 Bucket
- JDBC Driver
- Insomnia
- PostgreSQL


## Referência

 - [AWS Bucket S3](https://aws.amazon.com/pt/s3/)
 - [Rest API with Spring Boot](https://spring.io/guides/tutorials/rest)


## Licença ©️ 

[MIT](https://choosealicense.com/licenses/mit/)

