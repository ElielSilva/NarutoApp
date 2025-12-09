# NarutoApp

## 👥 Integrantes da equipe
- Eliel
- Jadeilson
---

### Documentação do Projeto
## 📋 Visão Geral
Projeto Android desenvolvido em Kotlin utilizando Jetpack Compose para construção de interfaces modernas e reativas.
## 🛠️ Tecnologias e Versões
# Linguagens
- Kotlin: Linguagem principal do projeto
- Java: Suporte para bibliotecas legadas
- Gradle: Sistema de build e gerenciamento de dependências
# Bibliotecas Principais
Jetpack Compose
- Compose UI: Framework moderno para construção de interfaces declarativas
- Material Design 3: Componentes de UI seguindo diretrizes do Material Design
# Arquitetura
MVVM (Model-View-ViewModel)
- ViewModel: Gerenciamento de estado e lógica de negócio
- Lifecycle: Componentes conscientes do ciclo de vida
- ViewModelProvider.Factory: Padrão para injeção de dependências no ViewModel

# Navegação
- Compose Navigation: Sistema de navegação entre telas composables
## 🏗️ Padrões Arquiteturais Aplicados
MVVM (Model-View-ViewModel)
- Model: Representação dos dados (ex: Character)
- View: Composables como CharacterScreen
- ViewModel: CharacterViewModel gerencia estado e lógica
# Repository Pattern
- CharacterRepository: Camada de abstração para acesso aos dados
Separa a fonte de dados da lógica de negócio
# Factory Pattern
- CharacterViewModelFactory: Criação controlada de instâncias do ViewModel
Permite injeção de dependências de forma estruturada
Single Responsibility Principle
Cada classe tem uma responsabilidade única e bem definida
## 📱 Funcionalidades Implementadas
Tela de Personagens (CharacterScreen)
- Exibição de Lista: Apresentação de personagens em formato de lista
- Navegação: Callback onPersonagemClick para navegação ao detalhe
- Gerenciamento de Estado: Integração com CharacterViewModel
- Injeção de Dependências: ViewModel injetado via parâmetro com valor padrão
## ViewModel (CharacterViewModel)
- Gerenciamento do estado da tela de personagens
- Comunicação com o repositório de dados
- Exposição de dados observáveis para a UI
## 🔧 Configuração do Projeto
Build System
- Gradle: Automatização de build e gerenciamento de dependências
- Configuração modular e escalável

# Estrutura
- data/
- - dao/ # Interfaces do Room para persistência
- - entity/ # Entities do banco local
- - db/ # Configuração do Room Database
- models/ # Data classes: Character, Village
- repository/
- - character/ # Repositórios para personagens
- - village/ # Repositórios para vilas
- services/ # Retrofit API client e interfaces
- ui/
- - character/ # Screens e itens de UI de personagens
- - village/ # Screens e itens de UI de vilas
- utils/ # Helpers e classes utilitárias (ex: UiState)
- MainActivity.kt # Entry point do app

## 💻 Instruções de Instalação e Execução

1. Clone o repositório:  
```bash
git clone https://github.com/seu-usuario/NarutoApp.git
```

2. Abra o projeto no Android Studio.

3. Sincronize o Gradle:

```File > Sync Project with Gradle Files```

4. Execute o app:

5. Selecione o dispositivo/emulador

6. Clique em "Run"

Para testar a persistência e API:

Certifique-se de ter conexão com a internet (para dados da API)


#Prints e GIFs do App






Documentação gerada em: 08-12-2025
