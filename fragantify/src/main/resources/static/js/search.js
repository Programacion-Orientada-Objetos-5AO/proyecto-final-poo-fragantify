// search.js
(() => {
  const body   = document.body;
  const toggle = document.getElementById('searchToggle');   // la lupa del navbar
  const panel  = document.getElementById('slidingSearch');  // el contenedor deslizante

  if (!toggle || !panel) return;

  // Alterna la barra deslizante
  toggle.addEventListener('click', (e) => {
    e.preventDefault();
    body.classList.toggle('show-sliding-search');
    // focus en el input cuando abre
    if (body.classList.contains('show-sliding-search')) {
      const input = panel.querySelector('input[type="search"]');
      setTimeout(() => input && input.focus(), 120);
    }
  });

  // Cerrar con ESC
  window.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && body.classList.contains('show-sliding-search')) {
      body.classList.remove('show-sliding-search');
    }
  });

  // Cerrar si clickeás fuera del panel y fuera de la lupa
  document.addEventListener('click', (e) => {
    const clickedInsidePanel  = e.target.closest('#slidingSearch');
    const clickedOnToggle     = e.target.closest('#searchToggle');
    if (!clickedInsidePanel && !clickedOnToggle && body.classList.contains('show-sliding-search')) {
      body.classList.remove('show-sliding-search');
    }
  });

  // Evitar enviar vacío
  const form = panel.querySelector('form');
  const input = panel.querySelector('input[type="search"]');
  form?.addEventListener('submit', (e) => {
    if (!input || !input.value.trim()) e.preventDefault();
  });
})();
