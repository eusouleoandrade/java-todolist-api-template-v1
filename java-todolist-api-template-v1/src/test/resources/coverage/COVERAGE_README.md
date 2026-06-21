# 📊 Relatórios de Cobertura de Código

Este diretório contém os relatórios de análise de cobertura de código do projeto Java TodoList API.

## 📁 Arquivos Gerados

### 1. **COVERAGE_REPORT.html** (26 KB) - ⭐ Recomendado
Um relatório visual e interativo com:
- Dashboard com métricas principais
- Gráficos de cobertura por pacote
- Tabela de detalhamento por camada
- Recomendações de manutenção de qualidade

**Como abrir:**
```bash
open COVERAGE_REPORT.html
# ou
# Abra no navegador: file:///Users/leandroandrade/Repos/java-todolist-api-template-v1/java-todolist-api-template-v1/src/test/resources/coverage/COVERAGE_REPORT.html
```

---

### 2. **COVERAGE_SUMMARY.md** (6 KB)
Um sumário em Markdown com:
- Resumo executivo
- Estatísticas globais
- Cobertura por pacotes
- Próximos passos e recomendações de CI/CD

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
- **Cobertura de Instruções:** 100% ✅
- **Cobertura de Branches:** 100% ✅
- **Total de Classes:** 49 (100% cobertas)
- **Total de Métodos:** 188 (100% cobertos)
- **Total de Linhas:** 644 (100% cobertas)
- **Testes Executados:** 241 (100% sucesso)

### 🚨 Áreas com Baixa Cobertura (< 80%)
**Nenhuma!** Todas as classes e pacotes atingiram 100% de cobertura.

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

## 💡 Dicas e Boas Práticas

1. **Usar relatório HTML para análise visual** - Mais intuitivo
2. **Usar SUMMARY.md para documentação** - Fácil de compartilhar
3. **Revisar interativamente no JaCoCo** - Para análise detalhada das linhas
4. **Automatizar via CI/CD** - Garantir gates de qualidade para novos PRs (sugestão: mínimo de 95% de cobertura)

---

## 📊 Interpretação das Métricas

### Instruções (Instructions)
Linhas de código compiladas (bytecode). Métrica mais granular de cobertura.

### Branches
Caminhos de lógica condicional (if/else, switch, etc.).

### Linhas (Lines)
Linhas de código fonte cobertas por testes.

### Métodos (Methods)
Funções/métodos cobertos por testes.

### Classes
Arquivos de classe cobertos por testes.

---

**Última atualização:** 21 de junho de 2026  
**Gerado por:** JaCoCo 0.8.12
