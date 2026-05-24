class AppLanceBovino {

    static CONFIG = {
        seletores: {
            money: ".componentemoney",
            data: ".componentedata",
            dataManual: ".componentedatamanual",
            hora: ".componentehora",
            cpf: ".componentecpf",
            confirm: "[hx-confirm]:not([data-confirm-ready])",
            buscaContainer: ".searchable-select-container:not([data-initialized])",
        },
        maskMoney: {
            prefix: "",
            suffix: "",
            fixed: true,
            fractionDigits: 2,
            decimalSeparator: ",",
            thousandsSeparator: ".",
            autoCompleteDecimal: true,
        },
        datePicker: {
            dateFormat: "dd/mm/yyyy",
            customWeekDays: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
            customMonths: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
            customClearBTN: "Limpar",
            customCancelBTN: "Cancelar",
            theme: { theme_color: "rgb(31 41 55)" },
        },
        texto: {
            cancelar: "Cancelar",
            remover: "Remover",
            tituloConfirm: "Você tem certeza?",
        }
    };

    static Toast = Swal.mixin({
        toast: true,
        position: "center-end",
        iconColor: "white",
        customClass: { popup: "colored-toast" },
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        grow: "row",
        didOpen: (toast) => {
            toast.onmouseenter = Swal.stopTimer;
            toast.onmouseleave = Swal.resumeTimer;
        },
    });

    static csrfData = {
        header: null,
        token: null
    };

    static init() {
        console.log("[DEBUG] init chamado");
        this.lerCsrfDoHTML();
        // this.atualizarComponentes(); // Removido pois o htmx.onLoad chamará para o document inicial
        this.registrarListenersGlobais();
    }

    static atualizarComponentes(container = document) {
        console.log("[DEBUG] atualizarComponentes chamado.");
        this.prepararMoney(container);
        this.prepararData(container);
        this.prepararHora(container);
        this.prepararConfirmacoes(container);
        this.prepararBusca(container);
        this.prepararCpf(container);
    }

    static lerCsrfDoHTML() {
        const tokenMeta = document.querySelector('meta[name="_csrf"]');
        const headerMeta = document.querySelector('meta[name="_csrf_header"]');
        if (tokenMeta && headerMeta) {
            this.csrfData.token = tokenMeta.content;
            this.csrfData.header = headerMeta.content;
        }
    }

    static registrarListenersGlobais() {
        if (typeof htmx !== 'undefined') {
            htmx.onLoad((elt) => {
                console.log("[DEBUG] htmx.onLoad disparado.");
                this.atualizarComponentes(elt);
            });

            htmx.on("htmx:afterSettle", (evt) => {
                console.log("[DEBUG] htmx:afterSettle disparado.");
                // Lógica de Captura do CSRF (vinda da resposta)
                if (evt.detail.xhr) {
                    let header = "X-CSRF-TOKEN";
                    let token = evt.detail.xhr.getResponseHeader(header);
                    if (token == null) {
                        header = "X-XSRF-TOKEN";
                        token = evt.detail.xhr.getResponseHeader(header);
                    }
                    if (token) {
                        this.csrfData.header = header;
                        this.csrfData.token = token;
                    }
                }
            });
        }

        document.body.addEventListener("htmx:configRequest", (evt) => {
            evt.detail.headers["accept"] = "text/html-partial";
            if (evt.detail.verb !== "get") {
                if (this.csrfData.header != null && this.csrfData.token != null) {
                    evt.detail.headers[this.csrfData.header] = this.csrfData.token;
                }
            }
        });

        document.body.addEventListener("exibirAlerta", (evt) => {
            this.mostrarNotificacaoViaHeader(evt.detail);
        });

        if (!document.body.dataset.clickOutsideRegistered) {
            document.addEventListener("click", (event) => {
                const activeDropdown = document.querySelector(".custom-select-dropdown:not(.hidden)");
                if (activeDropdown && !event.target.closest(".searchable-select-container")) {
                    activeDropdown.classList.add("hidden");
                    const activeTrigger = activeDropdown.closest(".searchable-select-container").querySelector(".custom-select-trigger");
                    if (activeTrigger) {
                        this._removeFocus(activeTrigger);
                    }
                }
            });
            document.body.dataset.clickOutsideRegistered = "true";
        }
    }

    static prepararMoney(container = document) {
        let inputs = container.querySelectorAll(this.CONFIG.seletores.money);
        if (container.matches && container.matches(this.CONFIG.seletores.money)) {
            inputs = [container];
        }
        console.log(`[DEBUG] prepararMoney encontrou ${inputs.length} elementos.`);

        inputs.forEach((input) => {
            if (typeof SimpleMaskMoney !== 'undefined') {
                SimpleMaskMoney.setMask(input, this.CONFIG.maskMoney);
            }
            input.classList.remove("componentemoney");
        });
    }

    static prepararData(container = document) {
        let inputs = container.querySelectorAll(this.CONFIG.seletores.data);
        if (container.matches && container.matches(this.CONFIG.seletores.data)) {
            inputs = [container];
        }

        inputs.forEach((input) => {
            // Variável para guardar a referência do calendário
            let datePicker = null;

            // Função local para sincronizar o texto com o calendário
            const sincronizarCalendario = () => {
                if (datePicker && input.value.length === 10) {
                    const parts = input.value.split("/");
                    const d = new Date(+parts[2], parts[1] - 1, +parts[0]);
                    // Garante que é uma data válida antes de mudar o calendário
                    if (!isNaN(d.getTime())) {
                        datePicker.setFullDate(d);
                    }
                }
            };

            let apagando = false;
            input.addEventListener("keydown", (e) => { apagando = e.key === "Backspace"; });

            input.addEventListener("input", (e) => {
                if (apagando) return;
                let v = e.target.value.replace(/\D/g, "");
                if (v.length > 2 && v.length <= 4) {
                    v = v.substring(0, 2) + "/" + v.substring(2);
                } else if (v.length > 4) {
                    v = v.substring(0, 2) + "/" + v.substring(2, 4) + "/" + v.substring(4, 8);
                }
                e.target.value = v;

                // Tenta sincronizar instantaneamente se a data estiver completa
                sincronizarCalendario();
            });

            // Sincroniza também caso o usuário saia do campo ou cole um valor
            input.addEventListener("blur", sincronizarCalendario);

            input.classList.remove("componentedata");
            input.setAttribute("placeholder", "DD/MM/AAAA");
            input.setAttribute("maxlength", "10");

            // --- CALENDÁRIO VINCULADO AO ÍCONE ---
            if (typeof MCDatepicker !== 'undefined') {
                const btnId = "btn-" + input.id;
                const btn = (container.querySelector ? container.querySelector("#" + btnId) : null)
                    || document.getElementById(btnId);

                if (btn) {
                    // 1. Cria uma "âncora" invisível dentro do botão
                    const anchorId = "anchor-" + input.id;
                    let anchor = document.getElementById(anchorId);

                    if (!anchor) {
                        anchor = document.createElement("span");
                        anchor.id = anchorId;
                        // Fica invisível, não atrapalha o layout e não recebe foco do TAB
                        anchor.style.cssText = "position: absolute; width: 1px; height: 1px; opacity: 0; pointer-events: none;";
                        btn.appendChild(anchor);
                    }

                    // 2. Vincula o MCDatepicker à âncora fantasma (isso resolve a posição na tela)
                    datePicker = MCDatepicker.create({
                        ...this.CONFIG.datePicker,
                        el: "#" + anchor.id,
                        autoClose: false
                    });

                    // 3. Controle Manual: Dispara com Click
                    btn.addEventListener("click", (e) => {
                        e.preventDefault();
                        datePicker.open();
                    });

                    // 4. Controle Manual: Dispara com Espaço ou Enter (padrão de acessibilidade)
                    btn.addEventListener("keydown", (e) => {
                        if (e.key === " " || e.key === "Enter") {
                            e.preventDefault(); // Evita que a barra de espaço role a página para baixo
                            datePicker.open();
                        }
                    });

                    datePicker.onSelect((date, formatedDate) => {
                        input.value = formatedDate;

                        // Dispara o evento de input para garantir que o HTMX/Validação percebam a mudança
                        input.dispatchEvent(new Event('input', { bubbles: true }));
                    });

                    sincronizarCalendario();
                }
            }
        });
    }

    static prepararHora(container = document) {
        let inputs = container.querySelectorAll(this.CONFIG.seletores.hora);
        if (container.matches && container.matches(this.CONFIG.seletores.hora)) {
            inputs = [container];
        }
        console.log(`[DEBUG] prepararHora encontrou ${inputs.length} elementos.`);

        inputs.forEach((input) => {
            input.classList.remove("componentehora");
            if (typeof mdDateTimePicker !== 'undefined') {
                const dialog = new mdDateTimePicker.default({
                    type: "time", mode: true, inner24: true, cancel: this.CONFIG.texto.cancelar,
                });
                input.addEventListener("click", () => dialog.toggle());
                dialog.trigger = input;
                input.addEventListener("onOk", () => {
                    input.value = dialog.time.format("HH:mm");
                });
            }
        });
    }

    static prepararCpf(container = document) {
        let inputs = container.querySelectorAll(this.CONFIG.seletores.cpf);
        if (container.matches && container.matches(this.CONFIG.seletores.cpf)) {
            inputs = [container];
        }

        inputs.forEach((input) => {
            let apagando = false;

            input.addEventListener("keydown", (e) => {
                apagando = e.key === "Backspace";
            });

            input.addEventListener("input", (e) => {
                if (apagando) return;

                let v = e.target.value.replace(/\D/g, ""); // Remove tudo que não é número

                // Aplica a máscara 000.000.000-00 progressivamente
                if (v.length > 9) {
                    v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4");
                } else if (v.length > 6) {
                    v = v.replace(/(\d{3})(\d{3})(\d{1,3})/, "$1.$2.$3");
                } else if (v.length > 3) {
                    v = v.replace(/(\d{3})(\d{1,3})/, "$1.$2");
                }

                e.target.value = v;
            });

            // Configurações nativas do input
            input.classList.remove("componentecpf");
            input.setAttribute("placeholder", "000.000.000-00");
            input.setAttribute("maxlength", "14");
        });
    }

    static prepararConfirmacoes(container = document) {
        let elementos = container.querySelectorAll(this.CONFIG.seletores.confirm);
        if (container.matches && container.matches(this.CONFIG.seletores.confirm)) {
            elementos = [container];
        }
        console.log(`[DEBUG] prepararConfirmacoes encontrou ${elementos.length} elementos.`);

        elementos.forEach((elemento) => {
            elemento.addEventListener("htmx:confirm", (e) => this._dispararSweetAlert(e));
            elemento.setAttribute("data-confirm-ready", "true");
        });
    }

    static _dispararSweetAlert(e) {
        e.preventDefault();
        Swal.fire({
            title: AppLanceBovino.CONFIG.texto.tituloConfirm,
            text: e.detail.question,
            icon: "warning",
            showCancelButton: true,
            cancelButtonText: AppLanceBovino.CONFIG.texto.cancelar,
            confirmButtonText: AppLanceBovino.CONFIG.texto.remover,
            confirmButtonColor: "#3085d6",
        }).then((result) => {
            if (result.isConfirmed) {
                e.detail.issueRequest(true);
            }
        });
    }

    static mostrarNotificacaoViaHeader(dados) {
        if (dados) {
            this.Toast.fire({ icon: dados.tipo, title: dados.mensagem, timer: dados.intervalo || 3000 });
        }
    }

    static _applyFocus(trigger) {
        trigger.classList.remove("border-gray-300");
        trigger.classList.add("ring-1", "border-gray-500", "ring-gray-500");
    }

    static _removeFocus(trigger) {
        trigger.classList.remove("ring-1", "border-gray-500", "ring-gray-500");
        trigger.classList.add("border-gray-300");
    }

    static prepararBusca(container = document) {
        let containers = container.querySelectorAll(this.CONFIG.seletores.buscaContainer);
        if (container.matches && container.matches(this.CONFIG.seletores.buscaContainer)) {
            containers = [container];
        }
        console.log(`[DEBUG] prepararBusca encontrou ${containers.length} elementos.`);

        containers.forEach((container) => {
            container.dataset.initialized = "true";
            const trigger = container.querySelector(".custom-select-trigger");
            const dropdown = container.querySelector(".custom-select-dropdown");
            const optionsList = container.querySelector(".options-list");
            const selectedValueSpan = container.querySelector(".selected-value-span");
            const hiddenInput = container.querySelector(".hidden-select-input");

            if (trigger && dropdown) {
                trigger.addEventListener("click", (e) => {
                    const isCurrentlyOpen = !dropdown.classList.contains("hidden");
                    document.querySelectorAll(".custom-select-dropdown:not(.hidden)").forEach(d => {
                        if (d !== dropdown) {
                            d.classList.add("hidden");
                            const otherTrigger = d.closest(".searchable-select-container")?.querySelector(".custom-select-trigger");
                            if (otherTrigger) {
                                this._removeFocus(otherTrigger);
                            }
                        }
                    });
                    dropdown.classList.toggle("hidden");
                    if (!isCurrentlyOpen) {
                        this._applyFocus(trigger);
                    } else {
                        this._removeFocus(trigger);
                    }
                    e.stopPropagation();
                    if (!isCurrentlyOpen) {
                        const searchInput = dropdown.querySelector('input[type="text"]');
                        if (searchInput) {
                            searchInput.focus();
                        }
                    }
                });
            }

            if (optionsList) {
                optionsList.addEventListener("click", (event) => {
                    const option = event.target.closest("li");
                    if (option) {
                        if (selectedValueSpan) selectedValueSpan.textContent = option.textContent.trim();
                        if (hiddenInput) hiddenInput.value = option.dataset.value;
                        if (dropdown) dropdown.classList.add("hidden");
                        const trigger = container.querySelector(".custom-select-trigger");
                        if (trigger) {
                            AppLanceBovino._removeFocus(trigger);
                        }
                    }
                });
            }
        });
    }
}

// Inicialização automática
document.addEventListener("DOMContentLoaded", () => AppLanceBovino.init());
