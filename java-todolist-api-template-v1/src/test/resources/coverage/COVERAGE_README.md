# 📊 Relatórios de Cobertura de Código

Este diretório contém os relatórios de análise de cobertura de código do projeto Java TodoList API.

## 📁 Arquivos Gerados

### 1. **COVERAGE_REPORT.html** (26 KB) - ⭐ Recomendado
Um relatório visual e interativo com:
- Dashboard com métricas principais
- Gráficos de cobertura por pacote
- Áreas com baixa cobertura destacadas
- Recomendações prioritárias
- Tabelas detalhadas

**Como abrir:**
```bash
open COVERAGE_REPORT.html
# ou
# Abra no navegador: file:///Users/leandroandrade/Repos/java-todolist-api-template-v1/java-todolist-api-template-v1/COVERAGE_REPORT.html
```

---

### 2. **COVERAGE_SUMMARY.md** (6 KB)
Um sumário em Markdown com:
- Resumo executivo
- Estatísticas globais
- Áreas de baixa, média e excelente cobertura
- Recomendações prioritárias
- Próximos passos

**Como visualizar:**
```bash
cat COVERAGE_SUMMARY.md
# ou
# Abra em seu editor de Markdown favorito
```

---

### 3. **target/site/jacoco/index.html** (15 KB)
Relatório completo interativo do JaCoCo com:
- Análise de cobertura por pacote
- Drill-down em classes e métodos
- Visualização detalhada de linhas cobertas

**Como abrir:**
```bash
open target/site/jacoco/index.html
```

---

## 🎯 Resultados da Análise

### ✅ Estatísticas Globais
- **Cobertura de Instruções:** 93% ✅
- **Cobertura de Branches:** 82% ⚠️
- **Total de Classes:** 49 (100%)
- **Total de Métodos:** 188 (99.5%)
- **Total de Linhas:** 644 (93%)
- **Testes Executados:** 213 (100% sucesso)

### 🚨 Áreas com Baixa Cobertura (< 80%)
1. **presentation.interceptors** - 31% ❌ CRÍTICO
2. **shared.notification.abstractions** - 46% ❌ CRÍTICO
3. **application.exceptions** - 50% ⚠️ AVISO

### ⚡ Área com Cobertura Média
- **root package** - 88% (próximo de excelente)

### ✅ Áreas com Cobertura Excelente
- 12 pacotes com 100% de cobertura 🏆
- Incluindo: Use Cases, Controllers, Repositories, DTOs

---

## 🔧 Como Gerar um Novo Relatório

```bash
# Opção 1: Executar testes e gerar relatório
mvn clean test prepare-package

# Opção 2: Apenas gerar relatório (se os testes já foram executados)
mvn jacoco:report

# Opção 3: Visualizar relatório existente
open target/site/jacoco/index.html
```

---

## 📈 Próximas Ações (Prioridade)

### 🔴 Prioridade 1 - CRÍTICA (4-8 testes)
**Pacote:** `presentation.interceptors` (31%)
- Adicionar testes de integração para interceptadores
- Testar validação de headers customizados
- Validar comportamento de correlação de IDs

### 🔴 Prioridade 2 - CRÍTICA (8-12 testes)
**Pacote:** `shared.notification.abstractions` (46%)
- Implementar testes para abstrações de notificação
- Testar diferentes tipos de mensagens
- Validar cenários de erro

### 🟡 Prioridade 3 - MÉDIA (3-5 testes)
**Pacote:** `application.exceptions` (50%)
- Adicionar testes para exceções customizadas
- Testar mensagens de erro
- Validar status HTTP

---

## 💡 Dicas e Boas Práticas

1. **Usar relatório HTML para análise visual** - Mais intuitivo
2. **Usar SUMMARY.md para documentação** - Fácil de compartilhar
3. **Revisar interativamente no JaCoCo** - Para análise detalhada
4. **Automatizar via CI/CD** - Gerar relatório em cada build
5. **Estabelecer gate de cobertura** - Bloquear PR < 80%

---

## 📊 Interpretação das Métricas

### Instruções (Instructions)
Linhas de código compiladas. Métrica mais granular de cobertura.

### Branches
Caminhos de lógica condicional (if/else, switch, etc.).

### Linhas (Lines)
Linhas de código fonte cobertas por testes.

### Métodos (Methods)
Funções/métodos cobertos por testes.

### Classes
Arquivos de classe cobertos por testes.

---

## 🔍 Navegação nos Relatórios

### No COVERAGE_REPORT.html
1. Scroll para ver diferentes seções
2. Clique nas seções para expandir/colapsar
3. Use as badges para filtrar por status (CRÍTICO, AVISO, ÓTIMO)

### No target/site/jacoco/index.html
1. Clique em pacotes para drill-down
2. Clique em classes para ver cobertura de métodos
3. Clique em métodos para ver linhas cobertas

---

## 📞 Dúvidas?

- Consulte [Documentação JaCoCo](https://www.eclemma.org/jacoco/)
- Revise os testes em `src/test/java`
- Analise o código em `src/main/java`

---

**Última atualização:** 10 de maio de 2026  
**Gerado por:** JaCoCo 0.8.12
