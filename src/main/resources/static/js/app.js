document.addEventListener('DOMContentLoaded',()=>{
 const input=document.getElementById('search'),status=document.getElementById('status');
 const cards=[...document.querySelectorAll('.coupon-item')],empty=document.getElementById('no-results');
 function filter(){const q=(input?.value||'').trim().toLocaleLowerCase('hu');const s=status?.value||'all';let n=0;
 cards.forEach(c=>{const ok=(c.dataset.search||'').toLocaleLowerCase('hu').includes(q)&&(s==='all'||c.dataset.status===s);c.hidden=!ok;if(ok)n++;});
 if(empty)empty.hidden=n!==0||cards.length===0;}
 input?.addEventListener('input',filter);status?.addEventListener('change',filter);
 document.querySelectorAll('.copy-code').forEach(b=>b.addEventListener('click',async()=>{
  try{await navigator.clipboard.writeText(b.dataset.code||'');b.textContent='Másolva ✓';setTimeout(()=>b.textContent='Másolás',1800);}catch{b.textContent='Kijelöléshez másold kézzel';}
 }));
});
