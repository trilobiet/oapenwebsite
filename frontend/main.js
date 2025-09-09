/* main.js
 * OAPEN UI interactions (navbar, loaders, utilities)
 * Includes legacy code from scripts.js
 */

'use strict';

document.addEventListener('DOMContentLoaded', () => {
  // ===== Helpers ============================================================

  /**
   * Shorthand querySelectorAll into an array.
   * @param {string} sel - CSS selector
   * @param {ParentNode|Document} [root=document]
   * @returns {HTMLElement[]}
   */
  const $all = (sel, root = document) => Array.prototype.slice.call(root.querySelectorAll(sel), 0);

  /**
   * Safe innerHTML load via fetch with basic error swallow.
   * Accepts "url [space] selector" to extract a fragment.
   * @param {HTMLElement} el - target element
   * @param {string} urlWithOptionalSelector - "URL" or "URL CSS_SELECTOR"
   * @returns {Promise<void>}
   */
  async function loadHTML(el, urlWithOptionalSelector) {
    try {
      const raw = (urlWithOptionalSelector || '').trim();
      if (!raw) return;

      // Split "url selector" (first space separates them)
      const spaceIdx = raw.indexOf(' ');
      const url = (spaceIdx === -1 ? raw : raw.slice(0, spaceIdx)).trim();
      const selector = spaceIdx === -1 ? '' : raw.slice(spaceIdx + 1).trim();

      const res = await fetch(url, { credentials: 'same-origin' });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const text = await res.text();

      if (!selector) {
        el.innerHTML = text;
        return;
      }

      // Extract only the requested fragment(s)
      const doc = new DOMParser().parseFromString(text, 'text/html');
      const nodes = Array.from(doc.querySelectorAll(selector));
      el.innerHTML = nodes.map(n => n.outerHTML).join('') || '';
    } catch (e) {
      // whatever
    }
  }

  // ===== Navbar: burger + expand/condense on scroll =========================

  /**
   * Wire up Bulma burger: toggles 'bulma-is-active' on burger and its target,
   * and maintains aria-expanded.
   */
  (function initBurger() {
    // Get all "navbar-burger" elements
    const burgers = $all('.bulma-navbar-burger');
    burgers.forEach((el) => {
      el.addEventListener('click', (evt) => {
        evt.preventDefault();
        // Get the target from the "data-target" attribute
        const targetId = el.dataset.target || 'oa-nav';
        const target = document.getElementById(targetId) || document.getElementById('oa-nav');
        if (!target) return;

        // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
        const active = target.classList.toggle('bulma-is-active');
        el.classList.toggle('bulma-is-active', active);
        el.setAttribute('aria-expanded', active ? 'true' : 'false');
      });
    });
  })();

  /**
   * Navbar expand/condense on scroll
   */
  (function initHeaderScroll() {
    const header = document.getElementById('oa-header');
    if (!header) return;

    const expand   = () => header.classList.remove('is-condensed');
    const condense = () => header.classList.add('is-condensed');
    const onScroll = () => (window.scrollY <= 8 ? expand() : condense());

    onScroll();
    window.addEventListener('scroll', onScroll, { passive: true });
  })();

  // ===== Legacy behaviours (from scripts.js) ================================

  // make iframes full height
  (function autoHeightIframes() {
    $all('.oapen-snippet iframe').forEach((ifr) => {
      ifr.addEventListener('load', () => {
        try {
          const doc = ifr.contentDocument || ifr.contentWindow?.document;
          if (doc && doc.body) {
            ifr.style.height = doc.body.scrollHeight + 'px';
          }
        } catch (e) {
          // Cross-origin: cannot measure; ignore.
        }
      });
    });
  })();

  // ajaxloader
  (function initAjaxLoader() {
    $all('.ajaxloader').forEach((el) => {
      const src = el.getAttribute('data-src');
      if (!src) return;
      el.innerHTML = " <i class='fa fa-spinner fa-pulse fa-fw'></i><i>Connecting to library&hellip;</i>";
      loadHTML(el, src);
    });
  })();

  // ===== Featured Titles: Splide carousel =========
  (function initFeaturedTitles() {
    function mount(scope = document) {
      const opts = {
        type: 'loop',
        perPage: 6,
        gap: '1.5rem',
        arrows: true,
        pagination: false,
        drag: true,
        autoplay: true,
        interval: 7000,
        speed: 1200,
        easing: 'cubic-bezier(0.22, 1, 0.36, 1)',
        pauseOnHover: true,
        pauseOnFocus: true,
        wheel: false,
        breakpoints: {
          1280: { perPage: 5 },
          1024: { perPage: 4 },
          768:  { perPage: 3 },
          480:  { perPage: 2 },
        },
      };

      const roots = $all('.splide', scope);
      roots.forEach((root) => {
        if (root.__splide) return;
        root.__splide = new Splide(root, opts).mount();
      });
    }

    // SSR case
    mount(document);

    // Async loader case
    $all('.carouselloader').forEach(async (el) => {
      const src = el.getAttribute('data-src');
      if (!src) return;
      el.innerHTML = " <i class='fa fa-spinner fa-pulse fa-fw'></i><i>Connecting to library&hellip;</i>";
      await loadHTML(el, src);
      mount(el);
    });
  })();


  // wrap content tables in a horizontal scrolling div
  (function wrapTables() {
    const tables = $all('.content table');
    tables.forEach((tbl) => {
      if (tbl.closest('.oapen-table-wrapper')) return;
      const wrapper = document.createElement('div');
      wrapper.className = 'oapen-table-wrapper';
      tbl.parentNode.insertBefore(wrapper, tbl);
      wrapper.appendChild(tbl);

      const hint = document.createElement('div');
      hint.className = 'oapen-table-swipe';
      hint.textContent = 'swipe to view table';
      wrapper.parentNode.insertBefore(hint, wrapper);
    });
  })();

  // faqs
  (function initFaqs() {
    $all('.oapen-foldout h3').forEach((h3) => {
      h3.addEventListener('click', () => {
        h3.classList.toggle('expanded');
      });
    });
  })();
});