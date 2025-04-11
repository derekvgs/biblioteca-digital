          // URL del servlet
        const API_URL = '/test2/libros/';

       // Categorías estáticas para el frontend
        const categories = [
            { id: 1, name: 'Novela' },
            { id: 2, name: 'Historia' },
            { id: 3, name: 'Ciencia' },
            { id: 4, name: 'Informática' },
            { id: 5, name: 'Biografía' }
        ];

        // Cargar libros desde el servidor
        async function loadBooks() {
            try {
                const response = await fetch(API_URL);
                const books = await response.json();
                renderBookList(books);
            } catch (error) {
                console.error('Error cargando libros:', error);
                alert('No se pudieron cargar los libros');
            }
        }

      // Renderizar la lista de libros en la interfaz
        function renderBookList(books) {
            const bookList = document.getElementById('bookList');
            bookList.innerHTML = '';
            
            books.forEach(book => {
                const categoryName = categories.find(c => c.id === book.categoriaId)?.name || 'Sin Categoría';
                const bookItem = document.createElement('div');
                bookItem.classList.add('list-item');
                bookItem.innerHTML = `
                    <div>
                        <strong>${book.titulo}</strong><br>
                        Autor: ${book.autor}<br>
                        ISBN: ${book.isbn}<br>
                        Precio: €${book.precio.toFixed(2)} | Stock: ${book.stock}<br>
                        Categoría: ${categoryName}
                    </div>
                    <div>
                        <button onclick="deleteBook(${book.id})">Eliminar</button>
                    </div>
                `;
                bookList.appendChild(bookItem);
            });
        }

 // Agregar un nuevo libro al sistema
        async function addBook() {
            const titulo = document.getElementById('bookTitle').value;
            const autor = document.getElementById('bookAuthor').value;
            const isbn = document.getElementById('bookISBN').value;
            const precio = parseFloat(document.getElementById('bookPrice').value);
            const stock = parseInt(document.getElementById('bookStock').value);
            const categoriaId = parseInt(document.getElementById('bookCategory').value);

       // Validación básica de campos
            if (!titulo || !autor || !isbn || isNaN(precio) || isNaN(stock)) {
                alert('Por favor, complete todos los campos correctamente');
                return;
            }

            const newBook = {
                titulo,
                autor,
                isbn,
                precio,
                stock,
                categoriaId
            };

            try {
                const response = await fetch(API_URL, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(newBook)
                });

                if (response.ok) {
             // Recargar la lista de libros
                    loadBooks();

                     // Limpiar formulario
                    document.getElementById('bookTitle').value = '';
                    document.getElementById('bookAuthor').value = '';
                    document.getElementById('bookISBN').value = '';
                    document.getElementById('bookPrice').value = '';
                    document.getElementById('bookStock').value = '';
                    document.getElementById('bookCategory').selectedIndex = 0;
                } else {
                    const error = await response.json();
                    alert(`Error: ${error.error}`);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('No se pudo agregar el libro');
            }
        }

        // Eliminar un libro del sistema
        async function deleteBook(id) {
            try {
                const response = await fetch(`${API_URL}${id}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                   // Recargar la lista de libros
                    loadBooks();
                } else {
                    const error = await response.json();
                    alert(`Error: ${error.error}`);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('No se pudo eliminar el libro');
            }
        }

        // Filtrar libros por título
        async function filterBooks() {
            const searchTerm = document.getElementById('bookSearch').value.toLowerCase();
            
            try {
                const response = await fetch(API_URL);
                const books = await response.json();
                
                const filteredBooks = books.filter(book => 
                    book.titulo.toLowerCase().includes(searchTerm)
                );
                
                renderBookList(filteredBooks);
            } catch (error) {
                console.error('Error filtrando libros:', error);
            }
        }

        // Renderizar lista de categorías (como placeholder)
        function renderCategoryList() {
            const categoryList = document.getElementById('categoryList');
            categoryList.innerHTML = '';
            categories.forEach(category => {
                const categoryItem = document.createElement('div');
                categoryItem.classList.add('list-item');
                categoryItem.innerHTML = `
                    <div>
                        <strong>${category.name}</strong>
                    </div>
                `;
                categoryList.appendChild(categoryItem);
            });
        }

           // Configuración de pestañas para cambiar entre secciones
        document.querySelectorAll('.tab').forEach(tab => {
            tab.addEventListener('click', () => {
                document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
                document.querySelectorAll('.content').forEach(c => c.classList.remove('active'));
                
                tab.classList.add('active');
                document.getElementById(tab.dataset.target).classList.add('active');
            });
        });

          // Inicialización al cargar la página
        loadBooks();
        renderCategoryList();

        function addBook() {
  const title = document.getElementById("bookTitle").value.trim();
  const author = document.getElementById("bookAuthor").value.trim();
  const isbn = document.getElementById("bookISBN").value.trim();

  if (!title || !author || !isbn) {
    alert("Por favor completa título, autor e ISBN.");
    return;
  }

  // Aquí iría tu lógica de fetch o POST real al backend
  console.log("Libro válido, listo para enviar");
}