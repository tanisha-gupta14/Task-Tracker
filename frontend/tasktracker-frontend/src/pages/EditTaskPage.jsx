import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../services/api";

function EditTaskPage() {
  const { id, taskId } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    title: "",
    description: "",
    dueDate: "",
    priority: "MEDIUM",
    status: "OPEN"
  });

  // ✅ FETCH TASK
  useEffect(() => {
    const fetchTask = async () => {
      try {
        const res = await API.get(`/task-lists/${id}/tasks/${taskId}`);

        setForm({
          title: res.data.title || "",
          description: res.data.description || "",
          
          // ✅ FIXED DATE FORMAT
          dueDate: res.data.dueDate
            ? new Date(res.data.dueDate).toISOString().slice(0, 16)
            : "",

          priority: res.data.priority || "MEDIUM",
          status: res.data.status || "OPEN"
        });

      } catch (err) {
        console.error("FETCH ERROR:", err.response?.data || err.message);
      }
    };

    fetchTask();
  }, [id, taskId]);

  // ✅ HANDLE INPUT CHANGE
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const update = async () => {
  try {
    // ✅ CLEAN PAYLOAD (NO INVALID VALUES)
    const payload = {
  id: taskId, // ✅ REQUIRED
  title: form.title.trim(),
  description: form.description.trim(),
  priority: form.priority,
  status: form.status,
  dueDate: form.dueDate
    ? new Date(form.dueDate).toISOString()
    : null
};

    console.log("FINAL PAYLOAD:", payload);

    await API.put(
      `/task-lists/${id}/tasks/${taskId}`,
      payload
    );

    navigate(`/task-list/${id}`);

  } catch (err) {
    console.error("FULL ERROR:", err.response?.data);
  }
};

  return (
    <div className="container">
      <h1 className="page-title">🌸 Edit Task</h1>

      <div className="card" style={{ maxWidth: "400px" }}>

        <input
          className="input"
          name="title"
          value={form.title}
          onChange={handleChange}
          placeholder="Title"
        />

        <input
          className="input"
          name="description"
          value={form.description}
          onChange={handleChange}
          placeholder="Description"
        />

        {/* ✅ CORRECT datetime-local */}
        <input
          type="datetime-local"
          className="input"
          name="dueDate"
          value={form.dueDate}
          onChange={handleChange}
        />

        <select
          className="input"
          name="priority"
          value={form.priority}
          onChange={handleChange}
        >
          <option value="HIGH">HIGH</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="LOW">LOW</option>
        </select>

        <select
          className="input"
          name="status"
          value={form.status}
          onChange={handleChange}
        >
          <option value="OPEN">OPEN</option>
          <option value="CLOSED">CLOSED</option>
        </select>

        <button className="button" onClick={update}>
          Update
        </button>

      </div>
    </div>
  );
}

export default EditTaskPage;