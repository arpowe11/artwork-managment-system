
const getUser = async () => {
  const token = sessionStorage.getItem("token");
  if (!token) return null;

  try {
    const response = await fetch("https://ams-app-backend-chdndmaghpetfabk.canadacentral-01.azurewebsites.net/api/v1/auth/users", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    });

    if (!response.ok) return null;

    const data = await response.json();
    return data;
  } catch (err) {
    console.error("Error fetching user:", err);
    return null;
  }
};

export default getUser;
