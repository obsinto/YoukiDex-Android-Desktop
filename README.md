# YoukiDEX — configuração do Pad

Registro da instalação personalizada em 14/11/2026. Este fork preserva o código da versão instalada e as preferências verificadas no tablet. A decisão é continuar com o YoukiDEX e corrigir os problemas conforme aparecerem.

Fork: [obsinto/YoukiDex-Android-Desktop](https://github.com/obsinto/YoukiDex-Android-Desktop).

## Origem e versão

- Projeto original: [mrYouki/YoukiDex-Android-Desktop](https://github.com/mrYouki/YoukiDex-Android-Desktop), arquivado pelo autor em 15/09/2026.
- Base do código: `f80410273e5d27ac4ce5810e21efdaa30f69a9cb` (YoukiDEX 3.5).
- Pacote personalizado: `com.youki.dex.tasks`; namespace das classes: `com.youki.dex`.
- Nome instalado: **YoukiDEX (apps abertos)**.
- Versão instalada: `3.5.4-tasks-local`, código `3504`.
- Licença GPL-3.0 preservada em [LICENSE](LICENSE); [README original](docs/README-upstream.md) mantido como registro histórico do upstream.

## Objetivo e estado atual

| Item | Estado em 14/11/2026 |
|---|---|
| Barra mostrar somente apps com tarefas abertas | Implementado e testado com Shizuku; limite de 10 apps por orientação |
| Apps fixados na barra do YoukiDEX | Nenhum; `pinned.lst` e `dock_pinned.lst` ausentes no armazenamento privado |
| Botão circular da barra | Abre o próprio desktop do YoukiDEX |
| Tela inicial padrão do Android | Xiaomi: `com.miui.home/.launcher.Launcher` |
| YoukiDEX original, pacote `com.youki.dex` | Preservado com os dados; serviço de acessibilidade desligado e app parado durante os testes |
| Shizuku | Instalado, iniciado e autorizado para a cópia personalizada |
| MacroDroid | Instalado, mas nenhuma macro criada ou ativada nesta configuração |
| Vertical com Xiaomi e horizontal com YoukiDEX | Desejado, mas ainda não implementado; preferência é preservar o app aberto ao girar |
| Dock Xiaomi sobreposta | Identificada como janela do `com.miui.home`; não foi desativada nem foi descoberta uma chave de configuração para ela |
| Atalhos no desktop | Anki (`com.ichi2.anki`) e Duolingo (`com.duolingo`), usuário Android 0 |
| Ícones cortados no desktop | Layout corrigido e instalado na 3.5.4; ícones e nomes visíveis. Validação completa do arraste ainda pendente |

“Tarefas abertas” significa as tarefas existentes reportadas pelo Android, inclusive janelas em segundo plano. Não é uma lista de todos os processos em memória, nem uma exigência de que cada app esteja visível.

## Ambiente verificado

- Tablet Xiaomi, modelo `25040RP0AL`, codinome `taiko`.
- Android 16, HyperOS `OS3.0.305.0.WOVMIXM`.
- Tela capturada em paisagem: 2560 × 1600; densidade física 360 dpi.
- Shizuku oficial: pacote `moe.shizuku.privileged.api`, versão `13.6.0.r1086.2650830c`, projeto [RikkaApps/Shizuku](https://github.com/RikkaApps/Shizuku).

## Alterações no código já instalado

1. Pacote separado, nome de aplicativo próprio e destino dos atalhos ajustado para permitir coexistência com o original e preservar os dados antigos.
2. Serviço de acessibilidade identificado por `ComponentName(context, DockService::class.java)`, sem depender do ID do pacote original.
3. Parser de `am stack list` compatível com o formato do HyperOS (`taskId=123: pacote/.Activity`) e com o formato `baseIntent=...`.
4. Barra baseada nas tarefas retornadas pelo Shizuku; removida a alternativa que preenchia a barra com histórico de uso quando a consulta não retornava tarefas. Sem acesso disponível, a lista fica vazia.
5. Exclusão do próprio pacote, da cópia original, do launcher padrão e do System UI; um ícone por pacote e indicadores derivados da mesma lista de tarefas.
6. Atualização a cada dois segundos quando a dock está visível e a tela ligada, para detectar fechamento de tarefas sem evento de acessibilidade.
7. Proteção para não atualizar a lista antes de inicializar a interface do serviço. O erro de inicialização foi diagnosticado e corrigido.
8. Botão circular da barra abre `LauncherActivity` do YoukiDEX com `NEW_TASK | CLEAR_TOP`, mantendo Xiaomi como Home padrão.
9. Última exceção não tratada salva em `files/last_crash.txt`, dentro do app, para diagnóstico. Um arquivo antigo não comprova uma falha nova.
10. Seis testes unitários do parser e fixture do formato observado no HyperOS. Os nomes de dois apps de terceiros foram substituídos por exemplos na fixture pública, preservando o formato de entrada.
11. Atalhos do desktop usam a largura e a altura do layout XML medido, incluindo o rótulo, em vez de uma caixa fixa de 50 × 50 dp.
12. Posicionamento e colisão usam os retângulos completos das views; a posição procurada é limitada à área útil do desktop, descontando a barra. As posições continuam salvas nas chaves existentes `deskpos_*`.
13. Ao começar um arraste, o app cancela o toque longo agendado para evitar que o menu abra durante o movimento.
14. O rótulo do atalho respeita a preferência existente `single_line_labels` (uma ou duas linhas), sem criar configuração nova.

## Preferências atuais do app

Snapshot histórico da base 3.5.3, lido do pacote personalizado antes da correção dos atalhos. A atualização 3.5.4 preserva esse perfil e não acrescenta chaves de configuração. Não significa que todos esses valores foram alterados manualmente: alguns vieram da importação do original ou de inicialização do app. Chaves ausentes seguem os padrões do código desta versão.

Perfil importável: [config/pad-2026-11-14/profile.sdp](config/pad-2026-11-14/profile.sdp), no formato nativo de backup do YoukiDEX. A tabela abaixo é a representação legível do mesmo snapshot histórico, com 47 preferências.

O nome pessoal exibido no menu foi omitido dos arquivos públicos e preservado no backup privado; deve ser configurado localmente ao reproduzir em outro aparelho. O horário interno da última inicialização também foi omitido por ser um dado transitório.

| Chave | Tipo no XML | Valor observado |
|---|---|---|
| `activation_method` | `string` | `handle` |
| `allow_app_freeze` | `boolean` | `false` |
| `allow_sysapp_uninstall` | `boolean` | `false` |
| `always_floating` | `boolean` | `false` |
| `center_apps_btn` | `boolean` | `false` |
| `center_running_apps` | `boolean` | `true` |
| `custom_toasts` | `boolean` | `false` |
| `deskpos_com.ichi2.anki` | `string` | `0,0` |
| `disable_heads_up` | `boolean` | `false` |
| `disable_taskbar` | `boolean` | `false` |
| `dock_hover_show_hide` | `boolean` | `true` |
| `editor_preview_aspect` | `string` | `16_9` |
| `editor_preview_scale` | `string` | `65` |
| `enable_nav_apps` | `boolean` | `true` |
| `enable_nav_assist` | `boolean` | `false` |
| `enable_power_menu` | `boolean` | `false` |
| `enable_qs_battery` | `boolean` | `false` |
| `enable_qs_bluetooth` | `boolean` | `false` |
| `enable_qs_bottom_row` | `boolean` | `true` |
| `enable_qs_date` | `boolean` | `true` |
| `enable_qs_notif` | `boolean` | `false` |
| `enable_qs_pin` | `boolean` | `true` |
| `enable_qs_vol` | `boolean` | `true` |
| `enable_qs_wallpaper` | `boolean` | `false` |
| `enable_qs_wifi` | `boolean` | `true` |
| `enable_soft_reboot` | `boolean` | `false` |
| `enable_tiling` | `boolean` | `true` |
| `handle_position` | `string` | `start` |
| `hide_nav_buttons` | `boolean` | `false` |
| `hide_status_bar` | `boolean` | `false` |
| `icon_padding` | `string` | `5` |
| `max_running_apps` | `string` | `10` |
| `max_running_apps_landscape` | `string` | `10` |
| `onboarding_complete` | `boolean` | `true` |
| `prefer_last_display` | `boolean` | `false` |
| `qs_panel_tile_1` | `string` | `wifi` |
| `qs_panel_tile_2` | `string` | `bluetooth` |
| `qs_panel_tile_3` | `string` | `none` |
| `qs_panel_tile_4` | `string` | `none` |
| `scale_factor` | `string` | `1.0` |
| `shizuku_active_app_detection` | `boolean` | `true` |
| `shortcut_pinned` | `boolean` | `true` |
| `show_battery_level` | `boolean` | `false` |
| `show_resource_monitor` | `boolean` | `false` |
| `show_running_indicators` | `boolean` | `true` |
| `show_search_bar` | `boolean` | `true` |
| `uhid_mode_enabled` | `boolean` | `false` |

Os ajustes nativos de tamanho dos ícones e dos rótulos não estavam personalizados (padrões de 100%). Nesta versão eles são aplicados ao dock e à grade de aplicativos, mas não ao desenho dos atalhos do desktop.

### Arquivos de atalhos

Cópia versionada: [config/pad-2026-11-14/desktop.lst](config/pad-2026-11-14/desktop.lst). `files/desktop.lst` contém:

```text
com.ichi2.anki 0
com.duolingo 0
```

Só o Anki tinha posição explicitamente salva: `deskpos_com.ichi2.anki=0,0`. O Duolingo usa a posição calculada pelo app. Esses arquivos não são incluídos pela exportação nativa de preferências `.sdp`.

Para adicionar um atalho pela interface: abrir o menu de aplicativos (nove pontos), manter pressionado o app e escolher **Desktop / Área de trabalho**. Fixar no desktop é diferente de fixar na barra.

## Acessos e configurações de sistema

A cópia personalizada está com:

- Acessibilidade: `com.youki.dex.tasks/com.youki.dex.services.DockService`.
- Acesso a notificações: `com.youki.dex.tasks/com.youki.dex.services.NotificationService`.
- Shizuku: `moe.shizuku.manager.permission.API_V23` concedida.
- `android.permission.WRITE_SECURE_SETTINGS` e `android.permission.PACKAGE_USAGE_STATS` concedidas no pacote. A barra personalizada usa a consulta de tarefas via Shizuku.
- AppOps `SYSTEM_ALERT_WINDOW`, `WRITE_SETTINGS` e `MANAGE_EXTERNAL_STORAGE` em `allow`.
- Permissões de notificação, imagens, vídeos e conexão/varredura Bluetooth concedidas no pacote, conforme o provisionamento do próprio app.

Os valores abaixo também estão em [system-settings.json](config/pad-2026-11-14/system-settings.json), como snapshot histórico. O JSON não é um script para aplicar settings.

Comparação com o snapshot capturado antes desta etapa:

| Namespace/chave | Antes | Atual |
|---|---|---|
| `global/development_force_resizable_activities` | ausente | `1` |
| `secure/secure_overlay_settings` | `0` | `1` |
| `secure/enabled_accessibility_services` | `com.youki.dex/com.youki.dex.services.DockService` | `com.youki.dex.tasks/com.youki.dex.services.DockService` |
| `secure/enabled_notification_listeners` | `com.xiaomi.barrage/com.xiaomi.barrage.service.NotificationMonitorService` | `com.youki.dex.tasks/com.youki.dex.services.NotificationService` |

A troca do listener de notificações é um efeito do provisionamento do próprio app, que escreve essa lista diretamente. Ao implementar um instalador/rollback, preservar os demais listeners e serviços existentes no dispositivo; não copiar uma lista exclusiva do tablet para outro aparelho.

Valores relevantes que já estavam presentes e permaneceram iguais no snapshot final:

| Namespace/chave | Valor |
|---|---|
| `global/enable_freeform_support` | `1` |
| `global/force_resizable_activities` | `1` |
| `global/enable_non_resizable_multi_window` | `0` |
| `global/force_desktop_mode_on_external_displays` | `0` |
| `global/force_fsg_nav_bar` | `1` |
| `secure/navigation_mode` | `2` |
| `global/hide_gesture_line` | `0` |

A navegação foi temporariamente trocada durante um teste e depois restaurada para gestos. Não há mudança permanente desses três últimos valores nesta configuração. A linha de gestos do Android e a Floating Dock Xiaomi são elementos diferentes.

## Compilar a versão preservada

Ambiente usado: JDK 21 (compilação Kotlin/Java alvo 17), Android SDK Platform 35, Build Tools 36.1.0, Gradle Wrapper 8.11.1, AGP 8.10.1 e Kotlin 2.2.20. O upstream já traz os artefatos nativos distribuídos nessa base; não foi executada uma recompilação Rust nesta etapa.

Configure `sdk.dir` no `local.properties` local e execute a partir da raiz:

```bash
bash ./gradlew --no-daemon --console=plain --max-workers=4 :app:testDebugUnitTest :app:assembleDebug
```

Saída: `app/build/outputs/apk/debug/app-debug.apk`.

O APK instalado foi compilado em modo debug e assinado com a chave debug local. Para atualizar o pacote existente sem perder os dados, é necessário preservar a mesma chave e usar um `versionCode` compatível. A chave fica fora do Git; uma chave debug recriada em outra máquina gera assinatura diferente.

APKs preservados localmente em `artifacts/` (fora do GitHub):

| Versão | SHA-256 |
|---|---|
| 3.5.4-tasks-local — instalada | `b18602eada77051947506079ad5cae377d97acfd4fcc33619698f1d08a4e5fc4` |
| 3.5.3-tasks-local — base anterior | `4577db10fa4ec22dc96369f4cda97e1f39d84f343d5c2b5fecf77f6c0656cf52` |

Os hashes identificam os APKs já produzidos, sem prometer compilação binária idêntica em outra máquina.

## Instalação e restauração

1. Preservar o APK original, os dados e as listas atuais de serviços antes de mudar qualquer instalação.
2. Instalar a cópia assinada com `adb install -r caminho/do/app-debug.apk`.
3. Instalar/iniciar o Shizuku oficial e autorizar **YoukiDEX (apps abertos)**. O estado de execução do Shizuku precisa ser conferido novamente após reiniciar o tablet.
4. Conceder sobreposição e acessibilidade à cópia personalizada. No HyperOS observado, atualizar o APK revogou a sobreposição; foi necessário concedê-la novamente e religar o serviço de acessibilidade, preservando os demais serviços.
5. Copiar o [perfil versionado](config/pad-2026-11-14/profile.sdp) para o tablet com `adb push config/pad-2026-11-14/profile.sdp /sdcard/Download/YoukiDEX-pad.sdp`. No YoukiDEX personalizado, abrir as configurações avançadas e usar a opção de restaurar preferências para selecionar esse arquivo. Ajustar o nome exibido localmente.
6. Restaurar separadamente a lista de atalhos do desktop a partir do arquivo versionado ou recriar os atalhos pela interface. A exportação `.sdp` não inclui `desktop.lst`; além disso, o exportador nativo ignora conjuntos e valores contendo `://`. O backup privado dos XMLs cobre esses casos.
7. Abrir o desktop sem trocar o launcher padrão:

```bash
adb shell am start -W -n com.youki.dex.tasks/com.youki.dex.activities.LauncherActivity
```

Comandos básicos usados, com ID do pacote personalizado:

```bash
adb shell pm grant com.youki.dex.tasks android.permission.WRITE_SECURE_SETTINGS
adb shell appops set com.youki.dex.tasks SYSTEM_ALERT_WINDOW allow
```

A configuração atual não é uma instalação “100% automática” pronta: ainda falta um instalador idempotente com backup/rollback, preservação das listas de serviços, restauração de preferências/atalhos e verificações pós-instalação. Autorizações do Android e início do Shizuku também dependem do estado do dispositivo. O README registra o necessário para construir essa automação sem presumir que ela já existe.

### Retorno ao original

A instalação original não foi removida. Para voltar a usá-la, desligar o serviço da cópia personalizada e reativar o original pela interface. Restaurar preferências próprias se necessário. Para restaurar estado de sistema/listeners, usar o backup específico desse aparelho e preservar serviços adicionados depois; não restaurar dumps completos de `settings` indiscriminadamente.

## Validações da base 3.5.3

- Parser: seis testes unitários aprovados, sem falhas nem erros; a suíte foi reexecutada antes da publicação deste registro.
- Aplicativo Calculadora aberto apareceu na barra; sua tarefa fechada deixou de aparecer após a atualização periódica.
- Histórico de aplicativos deixou de preencher a lista como se fossem tarefas abertas.
- Botão circular acionado a partir das Configurações voltou à `LauncherActivity` da cópia personalizada; Xiaomi permaneceu como Home padrão.
- Serviço permaneceu estável após a correção da inicialização; o arquivo histórico de exceção não recebeu nova data nesses testes.

Esses resultados pertencem à validação da versão 3.5.3 nesta sessão. Não comprovam comportamento em outras versões de Android, após reinicialização ou após alterar o código.

## Validação da correção dos atalhos — 3.5.4

- Compilação final concluída com `:app:testDebugUnitTest :app:assembleDebug`; seis testes, zero falhas e zero erros.
- APK final instalado com `adb install -r`, preservando os dados. Sobreposição e serviço de acessibilidade restabelecidos após a revogação feita pelo HyperOS na atualização.
- Antes da correção, o UI dump mostrava atalhos de 113 × 113 px e imagens visíveis de 113 × 76 px, sem rótulos acessíveis. Após o ajuste de layout, os atalhos passaram a 216 × 240 px, com imagens inteiras de 126 × 126 px e nomes visíveis (AnkiDroid e Duolingo), na densidade observada de 360 dpi.
- A abertura do AnkiDroid pelo atalho foi observada durante a validação. Na última inspeção da compilação final, os dois ícones e nomes permaneciam visíveis e o arquivo histórico de exceção não tinha uma nova data de modificação.
- O teste de arraste revelou que o menu de toque longo podia abrir durante o movimento. O cancelamento desse toque foi implementado, compilado e instalado.
- A validação completa de arraste, colisão, borda e persistência após recriar o desktop **não foi concluída**: houve interferência de telas/transições do sistema nas tentativas e o tablet foi desconectado antes da rodada final. Esses cenários continuam pendentes; não foram considerados aprovados.
- O usuário informou que o comportamento observado por ele até a desconexão estava ótimo e solicitou publicar este estado.

O layout XML continua sendo a fonte das dimensões; os retângulos usados no posicionamento são derivados das views, sem cache separado de tamanhos. O backup privado anterior à atualização está em `private/shortcuts-before-3.5.4/`; as evidências e os scripts de diagnóstico desta sessão ficam em `private/shortcuts-validation-3.5.4/`, sem publicação de capturas ou dados do tablet.

## Pendências antes de novas mudanças

- Concluir os testes de arraste, colisão, limites da tela e persistência após recriar o desktop quando o tablet estiver disponível novamente.
- Decisão registrada em 14/11/2026: manter YoukiDEX. O SmartDock foi avaliado pelo código e pela atividade do repositório, mas não foi instalado; não há migração planejada nesta etapa.
- Definir posteriormente a automação por orientação e a solução para a dock Xiaomi, conforme decisão do usuário.
- Preservar o backup privado de assinatura/dados e do APK instalado fora do GitHub. No checkout local, esses arquivos ficam em `private/` e `artifacts/`, ignorados pelo Git; devem ter uma cópia adicional em armazenamento de backup. A chave de assinatura é necessária para futuras atualizações sem desinstalar o pacote.

Backups completos de preferências, dumps de sistema, dados do tablet, capturas de tela e chaves de assinatura ficam fora do repositório público. Este documento contém apenas o registro técnico necessário da configuração.

## Ordem de trabalho combinada

1. Fork com o código atual e registro completo da configuração.
2. Somente depois, corrigir e validar os atalhos cortados do desktop.

A configuração foi registrada primeiro; a correção dos atalhos foi implementada e instalada em seguida como 3.5.4. O estado atual está publicado para continuidade posterior, com a validação de arraste pendente e sem macros de orientação.
