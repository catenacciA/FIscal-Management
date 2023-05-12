#!/bin/sh

# Define variables
src_dir="src"
out_dir="out"
lib_dir="lib"
doc_dir="docs"

# Compile Java files
if ! find "$src_dir" -type f -name "*.java" -print0 | xargs -0 javac -d "$out_dir" -cp "$lib_dir/*"; then
    echo "Compilation failed"
    exit 1
fi

# Generate documentation
if ! javadoc -d "$doc_dir" -cp "$out_dir:$lib_dir/*" -subpackages controller:model:view -sourcepath "$src_dir" -private -quiet -Xdoclint:none -author; then
    echo "Documentation generation failed"
    exit 1
fi

# Copy resource files
if [ -d "$src_dir/resources" ]; then
    mkdir -p "$out_dir"
    cp -R "$src_dir/resources" "$out_dir/"
fi

# Run the application
if ! java -cp "$out_dir:$lib_dir/*" BudgetManagementApp; then
    echo "Execution failed"
    exit 1
fi