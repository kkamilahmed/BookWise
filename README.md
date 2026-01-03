# BookWise | Bookstore Management System

**BookWise** is a modular **JavaFX-based bookstore management system** designed to handle inventory, customer transactions, and dynamic reward tiers.  
Built with **object-oriented principles** and the **State Design Pattern**, it improves maintainability, extensibility, and user experience for bookstore operations.

---

## Features

- **Inventory Management**  
  Track books, quantities, and pricing efficiently.

- **Transaction Handling**  
  Process 50+ customer transactions, including sales and returns.

- **Dynamic Reward System**  
  State Design Pattern allows flexible reward tiers based on customer activity.

- **GUI with JavaFX**  
  Intuitive interface for browsing inventory, adding items to cart, and completing transactions.

- **Extensible & Modular Design**  
  Easily add new features or modify existing logic without disrupting the core system.

- **UML-Based Design**  
  System interactions modeled with **Class and Use Case Diagrams** for clarity and maintainability.

---

## System Architecture

```mermaid
flowchart TD
    Customer[Customer Interface]
    GUI[JavaFX GUI]
    Controller[Transaction & Inventory Controller]
    Inventory[Book Inventory (Database)]
    Reward[Reward State System]
    Receipt[Receipt / Billing]

    Customer --> GUI
    GUI --> Controller
    Controller --> Inventory
    Controller --> Reward
    Controller --> Receipt
