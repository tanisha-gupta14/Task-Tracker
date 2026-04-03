import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";
import { Trash2, Pencil } from "lucide-react";

function TaskListsPage() {
  const [lists, setLists] = useState([]);
  const navigate = useNavigate();

  // 📦 Fetch all task lists
  const fetchLists = async () => {
    try {
      const res = await API.get("/task-lists");
      setLists(res.data);
    } catch (err) {
      console.error("Error fetching lists", err);
    }
  };

  useEffect(() => {
    fetchLists();
  }, []);

  // ❌ Delete
  const deleteList = async (id) => {
    try {
      await API.delete(`/task-lists/${id}`);
      fetchLists();
    } catch (err) {
      console.error("Delete failed", err);
    }
  };

  return (
    <div className="container">

      {/* 🔝 Header */}
      <div className="top-bar">
        <h1 className="page-title">🌸 Task Lists</h1>

        <button
          className="button"
          onClick={() => navigate("/create-list")}
        >
          + Create List
        </button>
      </div>

      {/* 🃏 Cards */}
      <div className="grid">
        {lists.map((list) => (
          <div
            key={list.id}
            className="card"
            onClick={() => navigate(`/task-list/${list.id}`)}
          >

            {/* 🔥 ICONS INSIDE CARD */}
            <div
              className="icons"
              onClick={(e) => e.stopPropagation()}
            >
              <Pencil
                color="#ff4d88"
                onClick={() => navigate(`/edit-list/${list.id}`)}
              />

              <Trash2
                color="red"
                onClick={() => deleteList(list.id)}
              />
            </div>

            {/* 📄 Content */}
            <h3>{list.title}</h3>

            <p style={{ opacity: 0.7 }}>
              {list.description}
            </p>

            <p>
              📊 {list.count} tasks • {list.progress}%
            </p>

          </div>
        ))}
      </div>
    </div>
  );
}

export default TaskListsPage;