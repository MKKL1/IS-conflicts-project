import { useEffect, useState } from "react";
import { Alert } from "react-bootstrap";
import { useNotificationContext } from "../contexts/NotificationContext.tsx";
import { NotificationVariants } from "../NotificationVariants.ts";

export default function Notification({ message, variant }: { message: string, variant: NotificationVariants }) {
    const { flag } = useNotificationContext();
    const [isVisible, setIsVisible] = useState(true);
    const time = 4000;

    useEffect(() => {
        setIsVisible(true);

        const timeout = setTimeout(() => {
            setIsVisible(false);
        }, time);

        return () => clearTimeout(timeout);
    }, [flag]);

    return (
        <div style={{
            display: isVisible ? 'block' : 'none',
            position: "fixed",
            top: "75px",
            left: "50%",
            transform: "translateX(-50%)",
            zIndex: 1050 // Ustawienie wysokiego zIndex, aby powiadomienie było na wierzchu
        }}>
            <Alert variant={variant} style={{ width: '50vw', textAlign: 'center' }} >
                {message}
            </Alert>
        </div>
    );
}
