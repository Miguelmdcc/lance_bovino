document.addEventListener('DOMContentLoaded', () => {
    initDecimalMasks();
    
    // Garante que o HTMX mantenha a máscara funcionando após swaps de fragmentos
    document.body.addEventListener('htmx:afterSwap', () => {
        initDecimalMasks();
    });
});

function initDecimalMasks() {
    document.querySelectorAll('.componentedecimal').forEach(input => {
        if (input.dataset.maskInitialized) return;
        input.dataset.maskInitialized = true;

        input.addEventListener('input', (e) => {
            let value = e.target.value.replace(/\D/g, '');
            const casas = parseInt(e.target.dataset.casas) || 2; // Pega o valor passado no Thymeleaf
            
            if (value === '') return;
            
            // Garante preenchimento de zeros caso o número digitado seja curto
            while (value.length <= casas) {
                value = '0' + value;
            }
            
            const len = value.length;
            const inteiro = value.slice(0, len - casas).replace(/^0+(?=\d)/, '') || '0';
            const decimal = value.slice(len - casas);
            
            // Aplica os pontos separadores de milhar (padrão pt-BR)
            const inteiroFormatado = inteiro.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
            
            e.target.value = inteiroFormatado + ',' + decimal;
        });
    });
}