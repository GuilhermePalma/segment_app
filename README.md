<h1 align="center">Segment APP</h1>
<h3 align="center">Code Challenge Android</h3>

<div align="center">

  [!Funcionamento do APP](https://user-images.githubusercontent.com/54846154/169289636-549962a9-a59e-4b19-84e8-c86abc8e8d15.mp4)

</div>

## Sobre o Projeto

Segment APP é um APP Android criado a partir de um ``code challenge``. O Objetivo do APP é realizar
o Cadastro do segmento em que o Usuario se encontra.

O APP foi escrito em Kotlin, utilizando da Bliblioteca ``Retrofit`` do Jetpack Compose para realizar
as chamadas HTTPS, e da dependencia ``GSON`` para deserializar os JSONs obtidos. Outro aspecto, é a
utilização de ``Build Variants`` para alteração do ``Theme`` de uma Paleta Azul para Vermelha

## Executando o Projeto

As Classes e Recursos utilizados se encontream no caminho [app/src/main/](app/src/main/). Os
principais Diretorios usados são:

- [Configurações das Activities](app/src/main/java/com/guilhermepalma/companysegment/presenter/ui)
- [Configuração do Adapter Recycler View](app/src/main/java/com/guilhermepalma/companysegment/presenter/adapter/MerchantCategoryAdapter.kt)
- [Entidades Utilizadas](app/src/main/java/com/guilhermepalma/companysegment/domain)
- [Classes de Consultas de Dados (API)](app/src/main/java/com/guilhermepalma/companysegment/data)
- [Classes de Recursos Visuais Desenvolvidos](app/src/main/res)

### Pré-Requisitos

- IDE: [Android Studio](https://developer.android.com/studio) → Ambiente de Desenvolvimento
- Versinamento: [Git](https://git-scm.com/downloads) → Software de Gerenciamento de Versões

### Download

Inicie o Git Bash (Terminal/Console do [Git](https://git-scm.com/downloads)), e digite os Seguintes
Comandos

```
# 1- Baixe (Clone) esse Repositorio
git clone https://github.com/GuilhermePalma/segment_app.git

# 2- Acesse a Pasta do Projeto
cd segment_app
```

Nesse Diretorio, inicie o Android Studio, realize a sincronização ``Sync now`` do arquivo Gradle e
Execute o **"Build Gradle"**. Após isso, no canto inferior esquerdo, clique em ``Build Variants`` e
selecione o ``Theme`` que deseja executar

## Tecnologias Utilizadas

Esse projeto foi Desenvolvido no [Android Studio](https://developer.android.com/studio), utilizando
a Linguagem [Java](https://developer.android.com/docs), voltado ao desenvolvimento Nativo Android

- Dependencias
    - Widget: [Recycler View](https://developer.android.com/guide/topics/ui/layout/recyclerview)
    - Editor de
      Tema: [Color Tool](https://material.io/resources/color/#!/?view.left=0&view.right=1&secondary.color=c0c9e8&primary.color=303F9F&primary.text.color=ffffff)

## Contribuições

Esse Projeto está sob a [Licensa MIT](LICENSE), ou seja é Open Source. Qualquer contribuição será
Bem-Vinda 😄.

Sinta-se à vontade de abrir ou
solucionar [Issues](https://github.com/GuilhermePalma/Infinite_Scroll/issues)
e [Pull Requests](https://github.com/GuilhermePalma/Infinite_Scroll/pulls). Em casos de Duvidas,
abra uma **Issues** e utilize a label **help wanted**

[Este Repositorio](https://github.com/firstcontributions/first-contributions/blob/master/translations/README.pt_br.md)
pode te auxiliar no processo de Cotribuições em Projetos no Github
