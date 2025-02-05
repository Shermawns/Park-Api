<body>
  <h1>🚗 ParkHub - Sistema de Gerenciamento de Estacionamento 🅿️</h1>

  <p>Bem-vindo ao <strong>ParkHub</strong>, um sistema de gerenciamento de estacionamento desenvolvido para facilitar o controle de vagas, clientes, veículos e operações de estacionamento. Este projeto é uma API Rest com autenticação via JSON Web Token (JWT), projetado para atender às necessidades de estacionamentos modernos.</p>

  <h2>📋 Requisitos e Funcionalidades</h2>

  <h3>⚙️ Configurações Gerais</h3>
  <ul>
    <li>Configuração de Timezone e Locale do país.</li>
    <li>Sistema de auditoria para registrar a data de criação e a última modificação dos registros.</li>
    <li>Registro do usuário que criou e modificou cada registro.</li>
    <li>Configuração de acesso ao banco de dados para ambiente de desenvolvimento.</li>
  </ul>

  <h3>👤 Gestão de Usuários</h3>
  <ul>
    <li>Cadastro de usuários com e-mail único como username.</li>
    <li>Senha de no mínimo 6 caracteres.</li>
    <li>Perfis de administrador e cliente.</li>
    <li>Recuperação de usuários por ID.</li>
    <li>Alteração de senha apenas pelo próprio usuário autenticado.</li>
    <li>Listagem de todos os usuários pelo administrador.</li>
    <li>Documentação e testes de integração dos recursos.</li>
  </ul>

  <h3>🔐 Autenticação</h3>
  <ul>
    <li>Autenticação via JSON Web Token (JWT).</li>
    <li>Implementação de Spring Security para controle de acesso.</li>
    <li>Documentação e testes do sistema de autenticação.</li>
  </ul>

  <h3>🧑‍💼 Gestão de Clientes</h3>
  <ul>
    <li>Cadastro de clientes vinculado a um usuário.</li>
    <li>Dados como nome completo e CPF único.</li>
    <li>Recuperação de dados do cliente via token.</li>
    <li>Listagem de clientes pelo administrador.</li>
    <li>Documentação e testes de integração dos recursos.</li>
  </ul>

  <h3>🚙 Gestão de Vagas</h3>
  <ul>
    <li>Código único para cada vaga.</li>
    <li>Status de vaga (livre ou ocupada).</li>
    <li>Localização de vagas pelo código.</li>
    <li>Documentação e testes de integração dos recursos.</li>
  </ul>

  <h3>🚘 Gestão de Estacionamentos</h3>
  <ul>
    <li>Controle de entradas e saídas de veículos.</li>
    <li>Registro de dados como placa, marca, modelo, cor, data de entrada e CPF.</li>
    <li>Geração de número de recibo único.</li>
    <li>Cálculo de custo com base no tempo estacionado.</li>
    <li>Desconto de 30% após 10 estacionamentos completos.</li>
    <li>Operações de check-in e check-out restritas ao administrador.</li>
    <li>Listagem de estacionamentos por CPF ou recibo.</li>
    <li>Documentação e testes de integração dos recursos.</li>
  </ul>

  <h2>🛠️ Tecnologias Utilizadas</h2>
  <p>O ParkHub foi desenvolvido com as seguintes tecnologias:</p>
  <ul>
    <li>☕ Java 21</li>
    <li>🌱 Spring Boot 3.4.1</li>
    <li>📂 Spring Data JPA</li>
    <li>🔒 Spring Security</li>
    <li>🔑 JWT Token para autenticação</li>
    <li>🛠 Flyway para migração e versionamento do banco de dados</li>
    <li>🐘 PostgreSQL</li>
    <li>📜 Springdoc OpenAPI (Swagger) para documentação da API</li>
    <li>✨ Lombok para reduzir código repetitivo</li>
    <li>⚠️ Tratamento global de exceções</li>
    <li>🐳 Docker para virtualização e containerização</li>
    <li>🦫 DBeaver como ferramenta de gerenciamento de banco de dados</li>
  </ul>

  <h2>📄 Modelo UML</h2>
  <p>Abaixo está o modelo UML representando as entidades e seus relacionamentos no sistema:</p>

  ![Screenshot 2025-02-05 105104](https://github.com/user-attachments/assets/355ddfbd-36dc-4ea9-9f01-7a29c8c0465e)


  <h2>📌 Explicação das Relações</h2>
  <ul>
    <li><strong>User:</strong> Representa os usuários do sistema, podendo ser ADMIN ou CLIENT.</li>
    <li><strong>Client:</strong> Cada cliente tem um usuário associado e pode realizar operações no estacionamento.</li>
    <li><strong>ParkingSpot:</strong> Representa uma vaga de estacionamento, podendo estar livre ou ocupada.</li>
    <li><strong>Garage:</strong> Controla a entrada e saída de veículos no estacionamento, armazenando informações sobre modelo, placa, cor, tempo de permanência e valor cobrado.</li>
    <li><strong>Role e Permissões:</strong> Define as permissões de ADMIN e CLIENT, garantindo a segurança e o controle de acesso com Spring Security e JWT.</li>
  </ul>

  <h2>🚀 Como Executar o Projeto</h2>
  <ol>
    <li><strong>Clone o repositório:</strong>
      <pre><code>git clone https://github.com/Shermawns/ParkHub.git</code></pre>
    </li>
    <li><strong>Configuração do Docker:</strong>
      <p>Certifique-se de ter o Docker instalado e execute o seguinte comando para subir os containers:</p>
      <pre><code>docker-compose up -d</code></pre>
    </li>
    <li><strong>Configuração do Banco de Dados:</strong>
      <p>Utilize o DBeaver para se conectar ao PostgreSQL rodando no Docker. As credenciais de acesso estão no arquivo <code>docker-compose.yml</code>.</p>
    </li>
    <li><strong>Execute a API:</strong>
      <p>Com o ambiente configurado, execute a aplicação Spring Boot. A API estará disponível em <code>http://localhost:8080</code>.</p>
    </li>
    <li><strong>Acesse a Documentação:</strong>
      <p>Acesse a documentação da API via Swagger em <code>http://localhost:8080/swagger-ui.html</code>.</p>
    </li>
  </ol>

  <h2>📧 Contato</h2>
  <p>Para mais informações, entre em contato:</p>
  <ul>
    <li>📧 Email: <a href="mailto:seu-email@example.com">shermawns@gmail.com</a></li>
    <li>🌐 LinkedIn: <a href="https://www.linkedin.com/in/seu-linkedin">https://www.linkedin.com/in/shermawn/</a></li>
  </ul>
</body>
