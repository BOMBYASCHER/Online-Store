const productsContainer = document.getElementById('products');
const cart = document.getElementById('cart');

fetch('api/products')
  .then(response => response.json())
  .then(data => {
  for(const product of data) {
    const productElement = document.createElement('div');
    productElement.classList.add('item');
    productElement.id = 'product-id-' + product.id;
    productElement.innerHTML = `
      <img src="${product.image}" alt="">
      <h2>${product.title}</h2>
      <div class="price">$${product.price}</div>
      <button class="addCart">Add to cart</button>
    `;
    productElement.addEventListener('click', (e) => {
      e.preventDefault();
      fetch('api/cart', {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: product.id
      })
      .then(response => {
        if (response.ok) {
          addProductToCart(product);
        }
      });
    });
    productsContainer.appendChild(productElement);
  }
});

const addProductToCart = (product) => {
  const cartElement = document.createElement('div');
  cartElement.classList.add('item');
  cartElement.dataset.id = 'cart-product-id-' + product.id;
  cartElement.innerHTML = `
  <div class="image">
          <img src="${product.image}">
      </div>
      <div class="name">
      ${product.title}
      </div>
      <div class="totalPrice">$${product.price}</div>
      <div class="quantity">
          <span class="minus"><</span>
          <span class="plus">></span>
      </div>
  `;
  cart.appendChild(cartElement);
};
