import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Trash2, Pencil } from "lucide-react";
import API from "../services/api";

function TasksPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [tasks, setTasks] = useState([]);

  const fetchTasks = async () => {
    const res = await API.get(`/task-lists/${id}/tasks`);
    setTasks(res.data);
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const deleteTask = async (taskId) => {
    await API.delete(`/task-lists/${id}/tasks/${taskId}`);
    fetchTasks();
  };

  return (
    <div className="container">
      <div className="top-bar">
        <h1 className="page-title">🌸 Tasks</h1>

        <button
          className="button"
          onClick={() => navigate(`/task-list/${id}/create`)}
        >
          + Create Task
        </button>
      </div>
      
      <div className="grid">
        {tasks.map((task) => (
          <div key={task.id} className="card">

            <h3>{task.title}</h3>

            <p>{task.description}</p>

            {/* DATE FIX */}
            <p>
              📅 {task.dueDate
                ? new Date(task.dueDate).toLocaleString()
                : "No due date"}
            </p>

            <div>
              <span className={`badge ${task.priority.toLowerCase()}`}>
                {task.priority}
              </span>

              <span className="badge">
                {task.status}
              </span>
            </div>

            <div className="icons" style={{marginTop:"10px"}}>
              <Pencil
                color="#ff4d88"
                onClick={() => navigate(`/task-list/${id}/edit/${task.id}`)}
              />

              <Trash2
                color="red"
                onClick={() => deleteTask(task.id)}
              />
            </div>

          </div>
        ))}
      </div>
    </div>
  );
}

export default TasksPage;