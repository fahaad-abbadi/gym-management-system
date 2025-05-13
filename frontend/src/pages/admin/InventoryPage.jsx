import React, { useEffect, useState } from "react";
import Layout from "@/components/Layout";
import {
  getAllInventory,
  addInventoryItem,
  updateInventoryItem,
  deleteInventoryItem,
} from "@/services/shared/inventoryService";

const InventoryPage = () => {
  const [inventory, setInventory] = useState([]);
  const [form, setForm] = useState({
    item_name: "",
    quantity: "",
    purchase_price: "",
    selling_price: "",
  });
  const [editId, setEditId] = useState(null);
  const [message, setMessage] = useState("");

  const fetchInventory = async () => {
    const res = await getAllInventory();
    console.log(res.data.inventories);
    setInventory(res.data.inventories || []);
  };

  useEffect(() => {
    fetchInventory();
  }, []);

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(""), 3000);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editId) {
        await updateInventoryItem(editId, form);
        showMessage("Item updated successfully.");
      } else {
        await addInventoryItem(form);
        showMessage("Item added successfully.");
      }
      setForm({
        item_name: "",
        quantity: "",
        purchase_price: "",
        selling_price: "",
      });
      setEditId(null);
      fetchInventory();
    } catch (err) {
      showMessage(err.message || "Error saving item.");
    }
  };

  const handleEdit = (item) => {
    setEditId(item.item_id);
    setForm({
      item_name: item.item_name,
      quantity: item.quantity,
      purchase_price: item.purchase_price,
      selling_price: item.selling_price,
    });
  };

  const handleDelete = async (id) => {
    if (window.confirm("Delete this item?")) {
      await deleteInventoryItem(id);
      showMessage("Item deleted.");
      fetchInventory();
    }
  };

  return (
    <Layout>
      <div className="max-w-5xl mx-auto mt-10">
        <h1 className="text-2xl font-bold text-green-50 mb-6">Gym Equipment Inventory</h1>

        {message && (
          <div className="bg-green-100 text-green-800 p-3 rounded mb-4">
            {message}
          </div>
        )}

        <form
          onSubmit={handleSubmit}
          className="bg-white p-4 rounded shadow border mb-10 space-y-4"
        >
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input
              type="text"
              placeholder="Equipment Name"
              value={form.item_name}
              onChange={(e) => setForm({ ...form, item_name: e.target.value })}
              required
              className="border px-3 py-2 rounded w-full"
            />
            <input
              type="number"
              placeholder="Quantity"
              value={form.quantity}
              onChange={(e) => setForm({ ...form, quantity: e.target.value })}
              required
              className="border px-3 py-2 rounded w-full"
            />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input
              type="number"
              placeholder="Purchase Price"
              value={form.purchase_price}
              onChange={(e) =>
                setForm({ ...form, purchase_price: e.target.value })
              }
              required
              className="border px-3 py-2 rounded w-full"
            />
            <input
              type="number"
              placeholder="Selling Price"
              value={form.selling_price}
              onChange={(e) =>
                setForm({ ...form, selling_price: e.target.value })
              }
              required
              className="border px-3 py-2 rounded w-full"
            />
          </div>

          <button
            type="submit"
            className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded font-semibold"
          >
            {editId ? "Update Item" : "Add Item"}
          </button>
        </form>

        <div className="grid gap-4">
          {inventory.length === 0 ? (
            <p className="text-gray-500">No equipment in inventory.</p>
          ) : (
            inventory.map((item) => (
              <div
                key={item.itemId}
                className="bg-white p-4 rounded shadow border flex justify-between items-center"
              >
                <div>
                  <h3 className="text-lg font-semibold">{item.itemName}</h3>
                  <p className="text-sm text-gray-500">Qty: {item.quantity}</p>
                  <p className="text-sm text-gray-500">
                    Purchase: ${item.purchasePrice} | Selling: $
                    {item.sellingPrice}
                  </p>
                </div>

                <div className="flex gap-2">
                  <button
                    onClick={() => handleEdit(item)}
                    className="text-indigo-600 hover:text-indigo-800 font-medium"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(item.itemId)}
                    className="text-red-600 hover:text-red-800 font-medium"
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </Layout>
  );
};

export default InventoryPage;
