document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("user"));

  if (!user) {
    window.location.href = "login.html";
    return;
  }

  const nav = document.createElement("nav");
  nav.className = "navbar navbar-expand-lg navbar-dark bg-dark mb-4";

  let navLinks = "";

  if (user.role === "USER") {
  navLinks = `
    <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
    <li class="nav-item"><a class="nav-link" href="book-list.html">Books</a></li>
    <li class="nav-item">
      <a class="nav-link" href="cart.html">
        Cart <span id="cart-count" class="badge bg-danger">0</span>
      </a>
    </li>
    <li class="nav-item"><a class="nav-link" href="my-orders.html">My Orders</a></li>
    <li class="nav-item"><a class="nav-link" href="profile.html">Profile</a></li>
  `;
}
  else if (user.role === "ADMIN") {
    navLinks = `
      <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
      <li class="nav-item"><a class="nav-link" href="admin-dashboard.html">Dashboard</a></li>
      <li class="nav-item"><a class="nav-link" href="admin-add-book.html">Add Book</a></li>
    `;
  }

  nav.innerHTML = `
    <div class="container">
      <a class="navbar-brand" href="#">📚 Bookstore ${user.role === "ADMIN" ? "Admin" : ""}</a>
      
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
          ${navLinks}
        </ul>
        <div class="text-white mt-2 mt-lg-0">
          👋 Hello, ${user.name}
          <button class="btn btn-sm btn-outline-light ms-3" onclick="logout()">Logout</button>
        </div>
      </div>
    </div>
  `;

  document.body.prepend(nav);

  // 👉 Load cart count only for USER
  if (user.role === "USER") {
    updateCartCount();
  }
});

function logout() {
  localStorage.removeItem("user");
  window.location.href = "login.html";
}

// 👇 Function to update cart badge
function updateCartCount() {
  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user?.id;

  if (!userId) return;

  fetch(`http://localhost:8080/api/cart/user/${userId}`)
    .then(res => res.json())
    .then(data => {
      const count = data.length;
      const badge = document.getElementById("cart-count");
      if (badge) {
        badge.innerText = count;
      }
    })
    .catch(err => {
      console.error("Error fetching cart count:", err);
    });
}
