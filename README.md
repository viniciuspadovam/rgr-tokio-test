# PROVA TESTE

## Criar uma API rest utilizando JPA

OBS: a conexão do banco já esta configurada via H2.

Para mapeamento das entidades verificar o arquivo (data.sql).

A API deverá conter as seguintes características:

1. CRUD de usuários
2. CRUD dos endereços do usuário

**Consumir um serviço rest**

Você devera consumir o seguinte serviço para retorna o endereço dado o CEP

Url: https://api.brasilaberto.com/v1/zipcode/{cep}

Exemplo de chamada
```sh
# Request
$ curl  https://api.brasilaberto.com/v1/zipcode/01001000

# Response
{
    "meta": {
        "currentPage": 1,
        "itemsPerPage": 1,
        "totalOfItems": 1,
        "totalOfPages": 1
    },
    "result": {
        "street": "Praça da Sé",
        "complement": "lado ímpar",
        "district": "Sé",
        "districtId": "1",
        "city": "São Paulo",
        "cityId": "1",
        "ibgeId": "3550308",
        "state": "São Paulo",
        "stateIbgeId": null,
        "stateShortname": "SP",
        "zipcode": "01001000"
    }
}
```


## Interface (Opcional)

Criar uma interface consumindo os serviços desenvolvidos utilizando uma das tecnologias abaixo:

1. Angular
2. Vue
3. Thymeleaf

