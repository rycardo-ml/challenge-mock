# challenge-mock

A app esta separada em 3 flavors:
  - Complete: Possui as duas funcionalidade formulario e produtos (o formulário pode ser acessado pela top bar na listagem de produtos);
  - Form: Mostra somente o ecrã de formulário;
  - Produto: Mostra os ecrãs de listagem de produto e detalhes;

App pode ser executada normalmente via o Android Studio selecionando qualquer uma das buildVariants de debug;

Listagem de produtos:
  - Ao abrir o ecrã de produtos será feito fetch dos produtos (pode continuar o fetch caso não tenha sido completado) e grava eles na base de dados;
  - Depois vai sempre retornar os produtos da base de dados;
  - Utilizando um lazyGrid para mostrar os produtos;
  - O Filtro esta sendo efetuado em um useCase, portanto esse useCase recebe a query e também a lista de produtos e depois retorna uma lista filtrada;

    TODO:
      - Paginação dos produtos;
      - Testar para o filtro ser utilizado direto na base de dados (fts4)
   
Detalhes do Produto:
  - Utilizando um componente externo para ter a imagem como um header que inicialmente ocupa 70% do ecrã e que pode ficar ocupando no minimo 40% do ecrã;
  - Esse ecrã recebe somente o id do produto e depois vai busca-lo a base de dados;
  - Utilizando glide para carregar a imagem;


    TODO:
      - Fazer com que possa se fazer o scroll apartir do header também, e também desativar o scroll quando já mostrar todos os items no ecrã;
      - Melhorar UI dos detalhes
      - Adicionar topAppBar para e adicionar botão de back;
      - No glide adicionar placehoder e também fallbackImage em caso de erro;
   
Formulário:
  - Aqui temos vário inputText, para todos os casos;
  - Validação esta sendo efetuado em um useCasse;
  - No caso de erro o campo irá ficar marcado em vermelho, mas não irá indicar qual o erro em especifico;

    TODO:
      - Criar um UseCase para validar cada campo;
      - Indicar qual o erro que aconteceu;
      - Melhorar em como mostrar o "sucesso";

Bibliotecas: 
  - Room: Para fazer cache dos produtos;
  - Jetpack compose: Fazer o layout dos ecrãs, fazer o controle da navegação;
  - Hilt: Fazer a injeção de depencias;
  - Retrofit: Cliente http para Android, utilizado para consumir as APIs;

Arquitetura (dividida por modulos):
  - App: Vai ficar a MainActivity e será o primeiro entryPoint da applicação
    - Depende de outros modulos de ui, commons, e também dos modulos de data para "saber" fazer as DI;

  - Common:
    Modulo que deve conter somente classes básicas como extensions, ou formatters;

  - Data:
    Modulos onde serão feitos acesso a base de dados, fazer chamadas as APIs, mas não tem regras de negocio;
    Pode depender de outros modulos de data, do modulo de domain e também common;

  - Domain:
    Esses modulos vão ser utilizados para ter regras de negocio especificas da app;
    Ele vai depender de modulos commons, e irá utilizar de DI para ter acesso aos modulos de Data;

  - Feature:
    Esses são os modulos de apresentação, views, viewModels, e irão comunicar com o Domain para ter acesso aos dados;
    Dependem de modulos de domain, common, e também do ui:core;

  - ui:core
    Modulo que possui views que podem ser compartilhadas por outros modulos de apresentação, tembém teria os temas, dimensions, cores, qualquer coisa especificas de views;

    
