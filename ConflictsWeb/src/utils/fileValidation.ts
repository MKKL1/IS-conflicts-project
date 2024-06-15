// utils/fileValidation.ts

export const validateFile = (file: File): string | null => {
    //sprawdzenie rozmiaru pliku (mniej niż 5MB)
    const maxSizeInBytes = 5 * 1024 * 1024; // 5MB
    if (file.size > maxSizeInBytes) {
        return "File size should be less than 5MB";
    }

    //sprawdzenie rozszerzenia pliku
    const allowedExtensions = [".xlsx", ".xls", ".csv"];
    const fileExtension = file.name.substring(file.name.lastIndexOf("."));
    if (!allowedExtensions.includes(fileExtension)) {
        return `File type not supported. Allowed types: ${allowedExtensions.join(", ")}`;
    }

    //tutaj dopisz dalej walidacje do poszczegolnych kolumn


    // zwróć null jesli cala walidacja przebiegla pomyslnie
    return null;
};
